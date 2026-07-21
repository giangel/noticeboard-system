package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/restore-notice")
public class RestoreNoticeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (!ValidationUtil.isPositiveInteger(idParam)) {
            response.sendRedirect(request.getContextPath() + "/admin/notices");
            return;
        }

        int noticeId = Integer.parseInt(idParam);
        Integer userId = SessionUtil.getUserId(request);

        try {
            boolean restored = noticeDAO.restoreNotice(noticeId);

            if (restored && userId != null) {
                Notice notice = noticeDAO.findById(noticeId);
                String title = notice != null ? notice.getTitle() : "Notice ID " + noticeId;
                auditLogDAO.insertLog(userId, "RESTORE_NOTICE", "Restored notice titled: " + title);
            }

            response.sendRedirect(request.getContextPath() + "/admin/notices");

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