package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.NoticeBookmarkDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.dao.NoticeViewDAO;
import com.noticeboard.dao.UserDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.model.Student;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final UserDAO userDAO = new UserDAO();
    private final NoticeViewDAO noticeViewDAO = new NoticeViewDAO();
    private final NoticeBookmarkDAO bookmarkDAO = new NoticeBookmarkDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = SessionUtil.getUserId(request);
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Student student = userDAO.findStudentByUserId(userId);
            if (student == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            noticeDAO.markExpiredNotices();

            int totalVisible = noticeDAO.countVisibleToStudent(student.getAcademicLevel(), student.getClassGroup());
            int unreadCount = noticeViewDAO.countUnreadForStudent(
                    student.getAcademicLevel(), student.getClassGroup(), student.getStudentId());
            int bookmarkCount = bookmarkDAO.countByStudentId(student.getStudentId());

            List<Notice> recentNotices = noticeDAO.findVisibleToStudent(
                    student.getAcademicLevel(), student.getClassGroup(), 5, 0);
            List<Notice> urgentNotices = noticeDAO.findByUrgency(AppConstants.URGENCY_URGENT, 5);

            request.setAttribute("student", student);
            request.setAttribute("totalVisible", totalVisible);
            request.setAttribute("unreadCount", unreadCount);
            request.setAttribute("bookmarkCount", bookmarkCount);
            request.setAttribute("recentNotices", recentNotices);
            request.setAttribute("urgentNotices", urgentNotices);

            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}