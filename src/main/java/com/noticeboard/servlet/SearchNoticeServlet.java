package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.NoticeCategoryDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.model.NoticeCategory;
import com.noticeboard.util.AppConstants;
import com.noticeboard.util.PaginationUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search-notices")
public class SearchNoticeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = ValidationUtil.trimOrEmpty(request.getParameter("keyword"));
        String categoryIdParam = request.getParameter("categoryId");
        String urgencyLevel = request.getParameter("urgencyLevel");
        String academicLevel = request.getParameter("academicLevel");
        String classGroup = request.getParameter("classGroup");
        String sortOrder = request.getParameter("sortOrder");

        Integer categoryId = ValidationUtil.isPositiveInteger(categoryIdParam)
                ? Integer.parseInt(categoryIdParam) : null;

        int pageNumber = PaginationUtil.parsePageNumber(request.getParameter("page"));
        int pageSize = AppConstants.DEFAULT_PAGE_SIZE;
        int offset = PaginationUtil.calculateOffset(pageNumber, pageSize);

        try {
            List<Notice> results = noticeDAO.searchAndFilter(keyword, categoryId, urgencyLevel,
                    academicLevel, classGroup, sortOrder, pageSize, offset);
            int totalRecords = noticeDAO.countSearchAndFilter(keyword, categoryId, urgencyLevel,
                    academicLevel, classGroup);
            int totalPages = PaginationUtil.calculateTotalPages(totalRecords, pageSize);

            List<NoticeCategory> categories = categoryDAO.findAll();

            request.setAttribute("results", results);
            request.setAttribute("categories", categories);
            request.setAttribute("keyword", keyword);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("urgencyLevel", urgencyLevel);
            request.setAttribute("academicLevel", academicLevel);
            request.setAttribute("classGroup", classGroup);
            request.setAttribute("sortOrder", sortOrder);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);

            request.getRequestDispatcher("/student/search-results.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}