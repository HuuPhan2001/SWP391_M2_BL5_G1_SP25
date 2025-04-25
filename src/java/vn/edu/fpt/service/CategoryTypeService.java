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
import java.util.List;
import vn.edu.fpt.dao.CategoryTypeDao;
import vn.edu.fpt.model.CategoryType;
import java.sql.SQLException;
import vn.edu.fpt.common.Common;
import vn.edu.fpt.common.Constant;
import vn.edu.fpt.model.Category;

/**
 *
 * @author MTTTT
 */
public class CategoryTypeService {

    private CategoryTypeDao categoryTypeDao = new CategoryTypeDao();

    public List<CategoryType> getAllCategoryTypes() {
        return categoryTypeDao.getAllCategoryTypes();
    }

    public void listAllCategoryTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String text_raw = request.getParameter("text");
        String status_raw = request.getParameter("status");

        String text;
        Integer status = null;

        text = text_raw != null && !"".equals(text_raw.trim()) ? text_raw : "";

        if (status_raw != null && !status_raw.isBlank()) {
            try {
                status = Integer.parseInt(status_raw);
            } catch (NumberFormatException e) {
                throw new RuntimeException(Constant.DATA_INVALID);
            }
        }

        List<CategoryType> list = categoryTypeDao.getAllCategoryTypesNonCheck(status, text);
        request.setAttribute("categoryTypes", list);
        request.getRequestDispatcher("./categoryType/ListCategoryType.jsp").forward(request, response);

    }

    public void createCategoryType(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String typeName = request.getParameter("typeName");
        String typeDesc = request.getParameter("typeDesc");
        String statusRaw = request.getParameter("status");

        CategoryType type = new CategoryType();
        type.setCategoryTypeName(typeName);
        type.setCategoryTypeDesc(typeDesc);
        type.setStatus((statusRaw != null) ? 1 : 0);

        if (typeName == null || typeName.trim().isEmpty() || typeName.length() > 100) {
            request.setAttribute("errorMessage", "Tên loại danh mục không được để trống và tối đa 100 ký tự.");
            request.setAttribute("categoryType", type);
            response.sendRedirect("new-category-type");
            return;
        }

        List<CategoryType> flag = categoryTypeDao.checkNameExist(typeName.trim());
        if (flag != null && !flag.isEmpty()) {
            Gson gson = new Gson();
            request.getSession().setAttribute("failedCategoryType", gson.toJson(type));
            request.setAttribute("errorMessage", "Tên loại đã tồn tại.");
            request.setAttribute("categoryType", type);
            response.sendRedirect("new-category-type");
            return;
        }

        categoryTypeDao.createCategoryType(type);
        request.getSession().setAttribute("successMessage", "Thêm loại danh mục thành công.");
        response.sendRedirect("list-category-type");
    }

    public void updateCategoryType(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        int typeId = Integer.parseInt(request.getParameter("id"));
        String typeName = request.getParameter("typeName");
        String typeDesc = request.getParameter("typeDesc");
        String statusRaw = request.getParameter("status");

        int status = (statusRaw != null) ? 1 : 0;

        CategoryType type = new CategoryType();
        type.setCategoryTypeId(typeId);
        type.setCategoryTypeName(typeName);
        type.setCategoryTypeDesc(typeDesc);
        type.setStatus(status);

        if (typeName == null || typeName.trim().isEmpty() || typeName.length() > 100) {
            request.setAttribute("errorMessage", "Tên loại danh mục không được để trống và tối đa 100 ký tự.");
            request.setAttribute("categoryType", type);
            response.sendRedirect("edit-category-type?id=" + typeId);
            return;
        }

        List<CategoryType> flag = categoryTypeDao.checkNameExist(typeName.trim());
        boolean isDuplicate = flag.stream().anyMatch(ct -> ct.getCategoryTypeId() != typeId);

        if (isDuplicate) {
            Gson gson = new Gson();
            request.getSession().setAttribute("failedCategoryType", gson.toJson(type));
            request.setAttribute("errorMessage", "Tên loại đã tồn tại.");
            request.setAttribute("categoryType", type);
            response.sendRedirect("edit-category-type?id=" + typeId);
            return;
        }

        categoryTypeDao.updateCategoryType(type);
        request.getSession().setAttribute("successMessage", "Cập nhật loại danh mục thành công.");
        response.sendRedirect("list-category-type");
    }

    public void deleteCategoryType(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int typeId = Integer.parseInt(request.getParameter("id"));
        categoryTypeDao.softDeleteCategoryType(typeId);
        response.sendRedirect("list-category-type");
    }

    public void updateCategoryTypeStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int typeId = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));

        categoryTypeDao.updateCategoryTypeStatus(typeId, status);
        response.sendRedirect("list-category-type");
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        request.getRequestDispatcher("./categoryType/FormCategoryType.jsp").forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int typeId = Integer.parseInt(request.getParameter("id"));
        CategoryType existingCategoryType = categoryTypeDao.getById(typeId);
        request.setAttribute("categoryType", existingCategoryType);
        request.getRequestDispatcher("./categoryType/FormCategoryType.jsp").forward(request, response);
    }

}
