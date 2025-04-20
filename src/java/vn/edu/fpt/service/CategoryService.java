/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.service;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import vn.edu.fpt.common.PaginatedResult;
import vn.edu.fpt.common.Pagination;
import vn.edu.fpt.dao.CategoryDao;
import vn.edu.fpt.model.Category;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.dao.CategoryTypeDao;
import vn.edu.fpt.dto.CategoryDto;
import vn.edu.fpt.model.CategoryType;

/**
 *
 * @author MTTTT
 */
public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();
    private CategoryTypeDao categoryTypeDao = new CategoryTypeDao();

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public void listAllCatePaging(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Pagination pagination = new Pagination();
        String text_raw = request.getParameter("text");
        String typeIdParam = request.getParameter("typeId");
        String status_raw = request.getParameter("status");

        int page = 1;
        if ((text_raw == null || text_raw.trim().isEmpty())
                && (typeIdParam == null || typeIdParam.trim().isEmpty())
                && (status_raw == null || status_raw.trim().isEmpty())) {
            page = Common.getIntParameter(request, "page", 1);
        }

        pagination.setPage(page);
        pagination.setSize(Common.getIntParameter(request, "size", 10));

        Integer typeId = null;
        String text;
        Integer status = null;

        text = text_raw != null && !"".equals(text_raw.trim()) ? text_raw : "";

        if (typeIdParam != null && !typeIdParam.isBlank()) {
            try {
                typeId = Integer.parseInt(typeIdParam);
            } catch (NumberFormatException e) {
                throw new RuntimeException(Constant.DATA_INVALID);
            }
        }

        if (status_raw != null && !status_raw.isBlank()) {
            try {
                status = Integer.parseInt(status_raw);
            } catch (NumberFormatException e) {
                throw new RuntimeException(Constant.DATA_INVALID);
            }
        }

        Set<String> validColumns = new HashSet<>(Arrays.asList(
                "category_id", "category_type_id", "category_name", "status", "create_at"
        ));

        pagination.setSortBy(Common.getValidSortColumn(request.getParameter("sortBy"), validColumns, "category_id"));
        pagination.setSortDirection(request.getParameter("sortDirection"));

        PaginatedResult<CategoryDto> paginatedCategorys = categoryDao.listAllCatePaging(pagination, typeId, status, text);

        List<CategoryType> list = categoryTypeDao.getAllCategoryTypes();

        request.setAttribute("listCategories", paginatedCategorys.getContent());
        request.setAttribute("pagination", paginatedCategorys.getPagination());
        request.setAttribute("categoryTypes", list);
        request.setAttribute("text", text);
        request.setAttribute("status", status);
        request.setAttribute("typeId", typeId);

        request.getRequestDispatcher("./category/ListCategory.jsp").forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<CategoryType> list = categoryTypeDao.getAllCategoryTypes();
        request.setAttribute("categoryTypes", list);
        request.getRequestDispatcher("./category/FormCategory.jsp").forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        Category existingCategory = categoryDao.getCategoryById(categoryId);
        List<CategoryType> listCateType = categoryTypeDao.getAllCategoryTypes();
        request.setAttribute("categoryTypes", listCateType);
        request.setAttribute("category", existingCategory);
        request.getRequestDispatcher("./category/FormCategory.jsp").forward(request, response);
    }

    public void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String banner = request.getParameter("banner");
            String typeIdRaw = request.getParameter("typeId");
            String statusRaw = request.getParameter("status");

            if (name == null || name.trim().isEmpty() || name.trim().length() >= 255 || name.startsWith(" ")) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                return;
            }

            int typeId = 0;
            try {
                typeId = Integer.parseInt(typeIdRaw);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid category type.");
                return;
            }

            if (description != null && (description.trim().length() >= 255 || description.startsWith(" "))) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                return;
            }

            if (banner != null && (banner.trim().length() >= 255 || banner.startsWith(" "))) {
                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.DATA_INVALID);
                return;
            }

            int status = (statusRaw != null) ? 1 : 0;

            Category newCategory = new Category();
            newCategory.setCategoryName(name.trim());
            newCategory.setCategoryDesc((description != null) ? description.trim() : "");
            newCategory.setCategoryTypeId(typeId);
            newCategory.setCategoryBanner((banner != null) ? banner.trim() : "");
            newCategory.setStatus(status);
            System.out.println(newCategory.toString());

            List<Category> flag = categoryDao.checkNameExist(newCategory.getCategoryName());
            if (flag != null && !flag.isEmpty()) {
                Gson gson = new Gson();
                request.getSession().setAttribute("failedCategory", gson.toJson(newCategory));

                request.getSession().setAttribute("errorMessage", Constant.ADD_FAILED + ": " + Constant.NAME_EXIST);

                request.setAttribute("category", newCategory);

                request.getRequestDispatcher("./category/FormCategory.jsp").forward(request, response);

            } else {
                categoryDao.createCategory(newCategory);
                request.getSession().setAttribute("successMessage", Constant.ADD_SUCCESS);
                response.sendRedirect("category");
            }
        } catch (IOException | NumberFormatException | SQLException e) {
            request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/category");
        }
    }

    public void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            Category existingCategory = categoryDao.getCategoryById(categoryId);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String banner = request.getParameter("banner");
            String typeIdRaw = request.getParameter("typeId");
            String statusRaw = request.getParameter("status");

            // Validate Name
            if (name == null || name.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.REQUIRED_FIELD);
                return;
            }

            int typeId = 0;
            try {
                typeId = Integer.parseInt(typeIdRaw);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid category type.");
                return;
            }

            int status = (statusRaw != null) ? 1 : 0;

            existingCategory.setCategoryName(name.trim());
            existingCategory.setCategoryDesc((description != null) ? description.trim() : "");
            existingCategory.setCategoryBanner((banner != null) ? banner.trim() : "");
            existingCategory.setCategoryTypeId(typeId);
            existingCategory.setStatus(status);
            existingCategory.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            List<Category> flag = categoryDao.checkNameExist(existingCategory.getCategoryName());
            if (flag != null && !flag.isEmpty() && flag.get(0).getCategoryId() != categoryId) {
                Gson gson = new Gson();
                request.getSession().setAttribute("failedCategory", gson.toJson(existingCategory));

                request.getSession().setAttribute("errorMessage", Constant.UPDATE_FAILED + ": " + Constant.NAME_EXIST);

                List<CategoryType> categoryTypes = categoryTypeDao.getAllCategoryTypes();
                request.setAttribute("categoryTypes", categoryTypes);

                request.setAttribute("category", existingCategory);
                request.getRequestDispatcher("./category/category-form.jsp").forward(request, response);
            } else {
                categoryDao.updateCategory(existingCategory);

                request.getSession().setAttribute("successMessage", Constant.UPDATE_SUCCESS);

                response.sendRedirect("category");
            }
        } catch (ServletException | IOException | NumberFormatException | SQLException e) {
            request.getSession().setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/category");
        }
    }

    public void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        System.out.println(categoryId);
        System.out.println("delete");
        categoryDao.deleteCategory(categoryId);

        response.sendRedirect("category");
    }

//    private User getCurrentUser(HttpServletRequest request) {
//
//        User user = (User) request.getSession().getAttribute("acc");
//        if (user == null) {
//            return null;
//        }
//        return categoryDao.getUserByFullName(user.getFullname() != null && !user.getFullname().isEmpty() ? user.getFullname() : user.getUsername());
//    }
    public void viewCategoryDetails(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDao.getCategoryById(categoryId);
        request.setAttribute("category", category);
        request.getRequestDispatcher("./category/DetailCategory.jsp").forward(request, response);
    }

    public void updateCategoryStatus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));
        categoryDao.updateCategoryStatus(categoryId, status);

        response.sendRedirect("category");
    }
}
