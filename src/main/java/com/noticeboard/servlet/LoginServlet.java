package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.noticeboard.dao.UserDAO;
import com.noticeboard.model.Administrator;
import com.noticeboard.model.Student;
import com.noticeboard.model.User;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PasswordUtil;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = ValidationUtil.trimOrEmpty(request.getParameter("username"));
        String password = request.getParameter("password");

        request.setAttribute("username", username);

        if (ValidationUtil.isNullOrEmpty(username) || ValidationUtil.isNullOrEmpty(password)) {
            request.setAttribute("errorMessage", "Please enter both username and password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.findByUsername(username);

            if (user == null || !PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            if (!user.isActive()) {
                request.setAttribute("errorMessage",
                        "This account has been deactivated. Please contact the administrator.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            SessionUtil.createUserSession(request, user.getUserId(), user.getUsername(),
                    user.getFullName(), user.getRoleName());

            if (AppConstants.ROLE_ADMIN.equals(user.getRoleName())) {
                Administrator admin = userDAO.findAdministratorByUserId(user.getUserId());
                if (admin != null) {
                    SessionUtil.setAdminId(request, admin.getAdminId());
                }
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");

            } else if (AppConstants.ROLE_STUDENT.equals(user.getRoleName())) {
                Student student = userDAO.findStudentByUserId(user.getUserId());
                if (student != null) {
                    SessionUtil.setStudentId(request, student.getStudentId());
                }
                response.sendRedirect(request.getContextPath() + "/student/dashboard");

            } else {
                request.setAttribute("errorMessage", "Unrecognized account role.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred during login.");
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}