/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import vn.edu.fpt.dao.AccountDAO;
import vn.edu.fpt.model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            // Lấy dữ liệu từ form
        String userName = request.getParameter("username");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password and Confirm Password do not match.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

//        try {
            AccountDAO dao = new AccountDAO();
            // Kiểm tra Username đã tồn tại chưa
            if (dao.isUsernameExists(userName)) {
                request.setAttribute("error", "Username is already in use.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }
            // Kiểm tra email đã tồn tại chưa
            if (dao.isEmailExists(email)) {
                request.setAttribute("error", "Email is already in use.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }

            // Tạo user mới
            User user = new User();
            user.setUserName(userName);
            user.setPhone(phone);
            user.setUserEmail(email);
            user.setUserPassword(password); // có thể mã hóa sau nếu cần
            user.setStatus(1);
            user.setRoleId(3); // user thường

            boolean success = dao.addAccount(user);

            if (success) {
                response.sendRedirect("Login.jsp");
            } else {
                request.setAttribute("error", "Failed to register. Please try again.");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Internal server error.");
//            request.getRequestDispatcher("register.jsp").forward(request, response);
//        }
    }
        
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
