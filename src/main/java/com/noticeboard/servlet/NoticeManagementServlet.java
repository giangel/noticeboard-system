package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PaginationUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/notices")
public class NoticeManagementServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = ValidationUtil.trimOrEmpty(request.getParameter("keyword"));
        String statusFilter = request.getParameter("status");
        int pageNumber = PaginationUtil.parsePageNumber(request.getParameter("page"));
        int pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        int offset = PaginationUtil.calculateOffset(pageNumber, pageSize);

        try {
            noticeDAO.markExpiredNotices();

            List<Notice> notices = noticeDAO.searchAdminNotices(keyword, statusFilter, pageSize, offset);
            int totalRecords = noticeDAO.countAdminNotices(keyword, statusFilter);
            int totalPages = PaginationUtil.calculateTotalPages(totalRecords, pageSize);

            request.setAttribute("notices", notices);
            request.setAttribute("keyword", keyword);
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);

            request.getRequestDispatcher("/admin/notices.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}