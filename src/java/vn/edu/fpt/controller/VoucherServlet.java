package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.fpt.model.User;
import vn.edu.fpt.service.VoucherService;

import java.io.IOException;
import java.sql.SQLException;

@MultipartConfig
public class VoucherServlet extends HttpServlet {
    private VoucherService voucherService;

    public void init() {
        voucherService = new VoucherService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println("VoucherServlet action: " + action);
        User user = (User) request.getSession().getAttribute("acc");

        if (user == null) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }

        try {
            if (user.getRoleId() == null) {
                request.getRequestDispatcher("./Login.jsp").forward(request, response);
            } else {
                Integer roleId = user.getRoleId();
                switch (roleId) {
                    case 1: // Admin
                        switch (action) {
                            case "/new-voucher":
                                voucherService.showNewForm(request, response);
                                break;
                            case "/edit-voucher":
                                voucherService.showEditForm(request, response);
                                break;
                            case "/view-voucher":
                                voucherService.showEditForm(request, response);
                                break;
                            case "/delete-voucher":
                                voucherService.deleteVoucher(request, response);
                                break;
                            case "/list-voucher":
                                voucherService.listAllVouchersPaging(request, response);
                                break;
                            default:
                                voucherService.listAllVouchersPaging(request, response);
                        }
                        break;
                    case 3: // Customer
                        voucherService.listAvailableVouchers(request, response);
                        break;
                    default:
                        request.getRequestDispatcher("./Login.jsp").forward(request, response);
                        break;
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        User user = (User) request.getSession().getAttribute("acc");

        if (user == null || user.getRoleId() == null || user.getRoleId() != 1) {
            request.getRequestDispatcher("./Login.jsp").forward(request, response);
            return;
        }

        try {
            switch (action) {
                case "/create-voucher":
                    voucherService.createVoucher(request, response);
                    break;
                case "/update-voucher":
                    voucherService.updateVoucher(request, response);
                    break;
                default:
                    voucherService.listAllVouchersPaging(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
}