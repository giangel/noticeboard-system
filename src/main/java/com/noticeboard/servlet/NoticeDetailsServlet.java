package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.noticeboard.dao.NoticeBookmarkDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.dao.NoticeViewDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice-details")
public class NoticeDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeViewDAO noticeViewDAO = new NoticeViewDAO();
    private final NoticeBookmarkDAO bookmarkDAO = new NoticeBookmarkDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (!ValidationUtil.isPositiveInteger(idParam)) {
            request.getRequestDispatcher("/404.jsp").forward(request, response);
            return;
        }

        int noticeId = Integer.parseInt(idParam);

        try {
            Notice notice = noticeDAO.findById(noticeId);

            if (notice == null) {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
                return;
            }

            Integer studentId = SessionUtil.getStudentId(request);

            if (studentId != null) {
                boolean alreadyViewed = noticeViewDAO.hasStudentViewedNotice(noticeId, studentId);
                noticeViewDAO.recordView(noticeId, studentId);

                if (!alreadyViewed) {
                    noticeDAO.incrementViewCount(noticeId);
                    notice.setViewCount(notice.getViewCount() + 1);
                }

                notice.setReadByCurrentStudent(true);
                notice.setBookmarkedByCurrentStudent(bookmarkDAO.isBookmarked(noticeId, studentId));
            }

            request.setAttribute("notice", notice);
            request.getRequestDispatcher("/student/notice-details.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}