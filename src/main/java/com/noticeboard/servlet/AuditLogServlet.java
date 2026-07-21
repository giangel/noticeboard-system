package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.model.AuditLog;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PaginationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/audit-logs")
public class AuditLogServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNumber = PaginationUtil.parsePageNumber(request.getParameter("page"));
        int pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        int offset = PaginationUtil.calculateOffset(pageNumber, pageSize);

        try {
            List<AuditLog> logs = auditLogDAO.findAllPaginated(pageSize, offset);
            int totalRecords = auditLogDAO.countAllLogs();
            int totalPages = PaginationUtil.calculateTotalPages(totalRecords, pageSize);

            request.setAttribute("logs", logs);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);

            request.getRequestDispatcher("/admin/audit-logs.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}