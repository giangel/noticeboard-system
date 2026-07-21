package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.NoticeCategoryDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.dao.UserDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.model.NoticeCategory;
import com.noticeboard.model.Student;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PaginationUtil;
import com.noticeboard.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/notices")
public class NoticeListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = SessionUtil.getUserId(request);
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int pageNumber = PaginationUtil.parsePageNumber(request.getParameter("page"));
        int pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        int offset = PaginationUtil.calculateOffset(pageNumber, pageSize);

        try {
            Student student = userDAO.findStudentByUserId(userId);
            if (student == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            noticeDAO.markExpiredNotices();

            List<Notice> notices = noticeDAO.findVisibleToStudent(
                    student.getAcademicLevel(), student.getClassGroup(), pageSize, offset);
            int totalRecords = noticeDAO.countVisibleToStudent(student.getAcademicLevel(), student.getClassGroup());
            int totalPages = PaginationUtil.calculateTotalPages(totalRecords, pageSize);

            List<NoticeCategory> categories = categoryDAO.findAll();

            request.setAttribute("notices", notices);
            request.setAttribute("categories", categories);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);

            request.getRequestDispatcher("/student/notices.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}