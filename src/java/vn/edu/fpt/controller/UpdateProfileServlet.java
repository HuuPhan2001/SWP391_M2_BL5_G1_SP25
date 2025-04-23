/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package vn.edu.fpt.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import vn.edu.fpt.dao.AccountDAO;
import vn.edu.fpt.model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/UpdateProfile"})
public class UpdateProfileServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateProfileServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProfileServlet at " + request.getContextPath() + "</h1>");
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
            request.setCharacterEncoding("UTF-8");

            int userId = Integer.parseInt(request.getParameter("userId"));
            String fullName = request.getParameter("userFullName");
            String email = request.getParameter("userEmail");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String avatar = request.getParameter("userAvatar");
            String cccd = request.getParameter("identificationNumber");

            // Lấy user hiện tại từ session
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("acc");

            // Cập nhật các trường có thể sửa
            currentUser.setUserFullName(fullName);
            currentUser.setUserEmail(email);
            currentUser.setPhone(phone);
            currentUser.setAddress(address);
            currentUser.setUserAvatar(avatar);
            currentUser.setIdentificationNumber(cccd);
            currentUser.setUpdateAt(Timestamp.from(Instant.now()));

            // Gọi DAO để update
            AccountDAO dao = new AccountDAO();
            dao.updateUser(currentUser);

            // Cập nhật lại session
            session.setAttribute("acc", currentUser);

            request.setAttribute("message", "Cập nhật thông tin thành công!");
            request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
        } catch (Exception e) {
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
