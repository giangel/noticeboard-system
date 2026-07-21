package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.dao.UserDAO;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/update-user-status")
public class UpdateUserStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        String newStatus = request.getParameter("newStatus");

        if (!ValidationUtil.isPositiveInteger(userIdParam)
                || (!AppConstants.ACCOUNT_ACTIVE.equals(newStatus) && !AppConstants.ACCOUNT_INACTIVE.equals(newStatus))) {
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        int targetUserId = Integer.parseInt(userIdParam);
        Integer adminUserId = SessionUtil.getUserId(request);

        try {
            boolean updated = userDAO.updateAccountStatus(targetUserId, newStatus);

            if (updated && adminUserId != null) {
                auditLogDAO.insertLog(adminUserId, "UPDATE_USER_STATUS",
                        "Changed status of user ID " + targetUserId + " to " + newStatus);
            }

            response.sendRedirect(request.getContextPath() + "/admin/users");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}