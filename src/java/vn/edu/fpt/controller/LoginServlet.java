/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import vn.edu.fpt.dao.AccountDAO;
import vn.edu.fpt.model.User;

/**
 *
 * @author ADMIN
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
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
        try {
// Lấy dữ liệu từ form
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String remember = request.getParameter("remember"); // checkbox

            AccountDAO dao = new AccountDAO();
            User user = dao.getAccount(username, password);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("acc", user);
                Integer roleId = user.getRoleId();
                if (roleId != null && roleId == 1) {
                    response.sendRedirect("adminDashboard.jsp");
                } else if (roleId != null && roleId == 3) {
                    response.sendRedirect("HomePage.jsp");
                } else {
                    response.sendRedirect("error.jsp");
                }
                // Nếu người dùng chọn "Remember me", tạo Cookie
                if ("on".equals(remember)) {
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie passwordCookie = new Cookie("password", password);

                    usernameCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                    passwordCookie.setMaxAge(7 * 24 * 60 * 60);

                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                } else {
                    // Nếu không chọn remember, xoá cookie cũ (nếu có)
                    Cookie usernameCookie = new Cookie("username", "");
                    Cookie passwordCookie = new Cookie("password", "");
                    usernameCookie.setMaxAge(0);
                    passwordCookie.setMaxAge(0);
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                }

            } else {
                // Sai thông tin đăng nhập
                request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);

            }
        } catch (Exception e) {
            // Ghi log ra console để kiểm tra lỗi
            e.printStackTrace();
            response.getWriter().println("Có lỗi xảy ra: " + e.getMessage());
        }
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
