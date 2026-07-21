package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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

@WebServlet("/admin/create-notice")
public class CreateNoticeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<NoticeCategory> categories = categoryDAO.findAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/create-notice.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = ValidationUtil.trimOrEmpty(request.getParameter("title"));
        String content = ValidationUtil.trimOrEmpty(request.getParameter("content"));
        String categoryIdParam = request.getParameter("categoryId");
        String urgencyLevel = request.getParameter("urgencyLevel");
        String targetAudience = request.getParameter("targetAudience");
        String academicLevel = ValidationUtil.trimOrEmpty(request.getParameter("academicLevel"));
        String classGroup = ValidationUtil.trimOrEmpty(request.getParameter("classGroup"));
        String expiryDateParam = request.getParameter("expiryDate");
        String action = request.getParameter("action");

        request.setAttribute("title", title);
        request.setAttribute("content", content);

        if (!ValidationUtil.isValidLength(title, 5, 200)) {
            forwardWithError(request, response, "Title must be between 5 and 200 characters.");
            return;
        }

        if (!ValidationUtil.isValidLength(content, 10, 10000)) {
            forwardWithError(request, response, "Notice content must be at least 10 characters.");
            return;
        }

        if (!ValidationUtil.isPositiveInteger(categoryIdParam)) {
            forwardWithError(request, response, "Please select a valid category.");
            return;
        }

        Integer userId = SessionUtil.getUserId(request);
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Notice notice = new Notice();
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

            notice.setCreatedBy(userId);
            notice.setExpiryDate(DateUtil.parseExpiryDate(expiryDateParam));

            if ("publish".equals(action)) {
                notice.setStatus(AppConstants.NOTICE_STATUS_PUBLISHED);
                notice.setDatePublished(new Timestamp(System.currentTimeMillis()));
            } else {
                notice.setStatus(AppConstants.NOTICE_STATUS_DRAFT);
                notice.setDatePublished(null);
            }

            int newNoticeId = noticeDAO.insertNotice(notice);

            if (newNoticeId != -1) {
                auditLogDAO.insertLog(userId, "CREATE_NOTICE",
                        "Created notice titled: " + title);
            }

            response.sendRedirect(request.getContextPath() + "/admin/notices");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        try {
            request.setAttribute("errorMessage", message);
            request.setAttribute("categories", categoryDAO.findAll());
            request.getRequestDispatcher("/admin/create-notice.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}