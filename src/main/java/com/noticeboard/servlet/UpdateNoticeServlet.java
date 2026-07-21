package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.dao.NoticeCategoryDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.model.NoticeCategory;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.DateUtil;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/update-notice")
public class UpdateNoticeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String noticeIdParam = request.getParameter("noticeId");
        String title = ValidationUtil.trimOrEmpty(request.getParameter("title"));
        String content = ValidationUtil.trimOrEmpty(request.getParameter("content"));
        String categoryIdParam = request.getParameter("categoryId");
        String urgencyLevel = request.getParameter("urgencyLevel");
        String targetAudience = request.getParameter("targetAudience");
        String academicLevel = ValidationUtil.trimOrEmpty(request.getParameter("academicLevel"));
        String classGroup = ValidationUtil.trimOrEmpty(request.getParameter("classGroup"));
        String expiryDateParam = request.getParameter("expiryDate");

        if (!ValidationUtil.isPositiveInteger(noticeIdParam)) {
            response.sendRedirect(request.getContextPath() + "/admin/notices");
            return;
        }

        int noticeId = Integer.parseInt(noticeIdParam);

        if (!ValidationUtil.isValidLength(title, 5, 200) || !ValidationUtil.isValidLength(content, 10, 10000)
                || !ValidationUtil.isPositiveInteger(categoryIdParam)) {
            forwardWithError(request, response, noticeId, "Please correct the highlighted fields and try again.");
            return;
        }

        Integer userId = SessionUtil.getUserId(request);
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Notice notice = new Notice();
            notice.setNoticeId(noticeId);
            notice.setTitle(title);
            notice.setContent(content);
            notice.setCategoryId(Integer.parseInt(categoryIdParam));
            notice.setUrgencyLevel(urgencyLevel != null ? urgencyLevel : AppConstants.URGENCY_NORMAL);
            notice.setTargetAudience(targetAudience != null ? targetAudience : AppConstants.TARGET_ALL);

            if (AppConstants.TARGET_LEVEL.equals(targetAudience) || AppConstants.TARGET_CLASS.equals(targetAudience)) {
                notice.setAcademicLevel(academicLevel);
            }
            if (AppConstants.TARGET_CLASS.equals(targetAudience)) {
                notice.setClassGroup(classGroup);
            }

            notice.setExpiryDate(DateUtil.parseExpiryDate(expiryDateParam));

            boolean updated = noticeDAO.updateNotice(notice);

            if (updated) {
                auditLogDAO.insertLog(userId, "UPDATE_NOTICE", "Updated notice titled: " + title);
            }

            response.sendRedirect(request.getContextPath() + "/admin/notices");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, int noticeId,
            String message) throws ServletException, IOException {
        try {
            Notice notice = noticeDAO.findById(noticeId);
            List<NoticeCategory> categories = categoryDAO.findAll();
            request.setAttribute("notice", notice);
            request.setAttribute("categories", categories);
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/admin/edit-notice.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}