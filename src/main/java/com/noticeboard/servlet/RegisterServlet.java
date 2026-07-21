package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.noticeboard.dao.RoleDAO;
import com.noticeboard.dao.UserDAO;
import com.noticeboard.model.Role;
import com.noticeboard.model.Student;
import com.noticeboard.model.User;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PasswordUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = ValidationUtil.trimOrEmpty(request.getParameter("fullName"));
        String username = ValidationUtil.trimOrEmpty(request.getParameter("username"));
        String email = ValidationUtil.trimOrEmpty(request.getParameter("email"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String matricNumber = ValidationUtil.trimOrEmpty(request.getParameter("matricNumber"));
        String academicLevel = ValidationUtil.trimOrEmpty(request.getParameter("academicLevel"));
        String classGroup = ValidationUtil.trimOrEmpty(request.getParameter("classGroup"));

        request.setAttribute("fullName", fullName);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("matricNumber", matricNumber);
        request.setAttribute("academicLevel", academicLevel);
        request.setAttribute("classGroup", classGroup);

        if (!ValidationUtil.isValidLength(fullName, 3, 100)) {
            request.setAttribute("errorMessage", "Full name must be between 3 and 100 characters.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidUsername(username)) {
            request.setAttribute("errorMessage",
                    "Username must be 4 to 50 characters, letters, numbers, and underscores only.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Please enter a valid email address.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (password == null || !password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Password and confirm password do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidMatricNumber(matricNumber)) {
            request.setAttribute("errorMessage", "Please enter a valid matric number.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (ValidationUtil.isNullOrEmpty(academicLevel)) {
            request.setAttribute("errorMessage", "Please select your academic level.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            if (userDAO.usernameExists(username)) {
                request.setAttribute("errorMessage", "This username is already taken.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            if (userDAO.emailExists(email)) {
                request.setAttribute("errorMessage", "This email is already registered.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            if (userDAO.matricNumberExists(matricNumber)) {
                request.setAttribute("errorMessage", "This matric number is already registered.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            Role studentRole = roleDAO.findByRoleName(AppConstants.ROLE_STUDENT);
            if (studentRole == null) {
                request.setAttribute("errorMessage",
                        "Student role is not configured. Please contact the administrator.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            String passwordHash = PasswordUtil.hashPassword(password);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPasswordHash(passwordHash);
            newUser.setFullName(fullName);
            newUser.setRoleId(studentRole.getRoleId());
            newUser.setAccountStatus(AppConstants.ACCOUNT_ACTIVE);

            int newUserId = userDAO.insertUser(newUser);

            if (newUserId == -1) {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            Student newStudent = new Student();
            newStudent.setUserId(newUserId);
            newStudent.setMatricNumber(matricNumber);
            newStudent.setAcademicLevel(academicLevel);
            newStudent.setClassGroup(classGroup);

            userDAO.insertStudent(newStudent);

            response.sendRedirect(request.getContextPath() + "/login.jsp?registered=true");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "A database error occurred during registration.");
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}