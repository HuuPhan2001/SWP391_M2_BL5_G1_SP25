package vn.edu.fpt.service;

import vn.edu.fpt.dao.VoucherDao;
import vn.edu.fpt.model.Voucher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class VoucherService {
    private VoucherDao voucherDao;

    public VoucherService() {
        voucherDao = new VoucherDao();
    }

    public void listAllVouchersPaging(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int page = 1;
        int pageSize = 10;
        String text = request.getParameter("text");
        Integer status = request.getParameter("status") != null && !request.getParameter("status").isEmpty() ? Integer.parseInt(request.getParameter("status")) : null;
        Integer minDiscount = request.getParameter("minDiscount") != null && !request.getParameter("minDiscount").isEmpty() ? Integer.parseInt(request.getParameter("minDiscount")) : null;
        Integer maxDiscount = request.getParameter("maxDiscount") != null && !request.getParameter("maxDiscount").isEmpty() ? Integer.parseInt(request.getParameter("maxDiscount")) : null;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("size") != null) {
            pageSize = Integer.parseInt(request.getParameter("size"));
        }

        List<Voucher> vouchers = voucherDao.getAllVouchers(text, status, minDiscount, maxDiscount, page, pageSize);
        int totalVouchers = voucherDao.getTotalVouchers(text, status, minDiscount, maxDiscount);
        int totalPages = (int) Math.ceil((double) totalVouchers / pageSize);

        request.setAttribute("vouchers", vouchers);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("text", text);
        request.setAttribute("status", status);
        request.setAttribute("minDiscount", minDiscount);
        request.setAttribute("maxDiscount", maxDiscount);
        request.getRequestDispatcher("./voucher/voucherList.jsp").forward(request, response);
    }

    public void listAvailableVouchers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Voucher> vouchers = voucherDao.getAvailableVouchers();
        request.setAttribute("vouchers", vouchers);
        request.getRequestDispatcher("./voucher/voucherList.jsp").forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("./voucher/formVoucher.jsp").forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        Voucher voucher = voucherDao.getVoucherById(voucherId);
        request.setAttribute("voucher", voucher);
        request.getRequestDispatcher("./voucher/formVoucher.jsp").forward(request, response);
    }

    public void createVoucher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Voucher voucher = new Voucher();
        voucher.setCode(request.getParameter("code"));
        voucher.setDescription(request.getParameter("description"));
        String discountAmount = request.getParameter("discount_amount");
        voucher.setDiscountAmount(discountAmount != null && !discountAmount.isEmpty() ? Integer.parseInt(discountAmount) : null);
        String minOrderAmount = request.getParameter("min_order_amount");
        voucher.setMinOrderAmount(minOrderAmount != null && !minOrderAmount.isEmpty() ? Integer.parseInt(minOrderAmount) : null);
        try {
            voucher.setStartDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("start_date")).getTime()));
            voucher.setEndDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("end_date")).getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Invalid date format.");
            response.sendRedirect(request.getContextPath() + "/new-voucher");
            return;
        }
        String quantity = request.getParameter("quantity");
        voucher.setQuantity(quantity != null && !quantity.isEmpty() ? Integer.parseInt(quantity) : null);
        String productId = request.getParameter("product_id");
        voucher.setProductId(productId != null && !productId.isEmpty() ? Integer.parseInt(productId) : null);
        voucher.setStatus(request.getParameter("status") != null ? 1 : 0);
        voucherDao.insertVoucher(voucher);
        request.getSession().setAttribute("successMessage", "Voucher created successfully.");
        response.sendRedirect(request.getContextPath() + "/list-voucher");
    }

    public void updateVoucher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Voucher voucher = new Voucher();
        voucher.setVoucherId(Integer.parseInt(request.getParameter("voucherId")));
        voucher.setCode(request.getParameter("code"));
        voucher.setDescription(request.getParameter("description"));
        String discountAmount = request.getParameter("discount_amount");
        voucher.setDiscountAmount(discountAmount != null && !discountAmount.isEmpty() ? Integer.parseInt(discountAmount) : null);
        String minOrderAmount = request.getParameter("min_order_amount");
        voucher.setMinOrderAmount(minOrderAmount != null && !minOrderAmount.isEmpty() ? Integer.parseInt(minOrderAmount) : null);
        try {
            voucher.setStartDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("start_date")).getTime()));
            voucher.setEndDate(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(request.getParameter("end_date")).getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Invalid date format.");
            response.sendRedirect(request.getContextPath() + "/edit-voucher?voucherId=" + voucher.getVoucherId());
            return;
        }
        String quantity = request.getParameter("quantity");
        voucher.setQuantity(quantity != null && !quantity.isEmpty() ? Integer.parseInt(quantity) : null);
        String productId = request.getParameter("product_id");
        voucher.setProductId(productId != null && !productId.isEmpty() ? Integer.parseInt(productId) : null);
        voucher.setStatus(request.getParameter("status") != null ? 1 : 0);
        voucherDao.updateVoucher(voucher);
        request.getSession().setAttribute("successMessage", "Voucher updated successfully.");
        response.sendRedirect(request.getContextPath() + "/list-voucher");
    }

    public void deleteVoucher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        voucherDao.deleteVoucher(voucherId);
        request.getSession().setAttribute("successMessage", "Voucher deleted successfully.");
        response.sendRedirect(request.getContextPath() + "/list-voucher");
    }
}