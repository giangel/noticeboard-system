package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.dao.UserDAO;
import com.noticeboard.model.AuditLog;
import com.noticeboard.model.Notice;
import com.noticeboard.util.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final UserDAO userDAO = new UserDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            noticeDAO.markExpiredNotices();

            int totalNotices = noticeDAO.countTotalNotices();
            int publishedCount = noticeDAO.countByStatus(AppConstants.NOTICE_STATUS_PUBLISHED);
            int draftCount = noticeDAO.countByStatus(AppConstants.NOTICE_STATUS_DRAFT);
            int archivedCount = noticeDAO.countByStatus(AppConstants.NOTICE_STATUS_ARCHIVED);
            int expiredCount = noticeDAO.countByStatus(AppConstants.NOTICE_STATUS_EXPIRED);
            int urgentCount = noticeDAO.countByUrgency(AppConstants.URGENCY_URGENT);
            int publishedThisMonth = noticeDAO.countPublishedThisMonth();
            int totalStudents = userDAO.countAllStudents();

            List<Notice> recentNotices = noticeDAO.findByStatus(AppConstants.NOTICE_STATUS_PUBLISHED, 5, 0);
            List<Notice> mostViewedNotices = noticeDAO.findMostViewed(5);
            List<AuditLog> recentActivity = auditLogDAO.findRecent(8);

            request.setAttribute("totalNotices", totalNotices);
            request.setAttribute("publishedCount", publishedCount);
            request.setAttribute("draftCount", draftCount);
            request.setAttribute("archivedCount", archivedCount);
            request.setAttribute("expiredCount", expiredCount);
            request.setAttribute("urgentCount", urgentCount);
            request.setAttribute("publishedThisMonth", publishedThisMonth);
            request.setAttribute("totalStudents", totalStudents);
            request.setAttribute("recentNotices", recentNotices);
            request.setAttribute("mostViewedNotices", mostViewedNotices);
            request.setAttribute("recentActivity", recentActivity);

            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}