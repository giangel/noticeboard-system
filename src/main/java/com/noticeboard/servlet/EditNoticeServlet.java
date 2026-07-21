package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.NoticeCategoryDAO;
import com.noticeboard.dao.NoticeDAO;
import com.noticeboard.model.Notice;
import com.noticeboard.model.NoticeCategory;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/edit-notice")
public class EditNoticeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeDAO noticeDAO = new NoticeDAO();
    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (!ValidationUtil.isPositiveInteger(idParam)) {
            response.sendRedirect(request.getContextPath() + "/admin/notices");
            return;
        }

        try {
            int noticeId = Integer.parseInt(idParam);
            Notice notice = noticeDAO.findById(noticeId);

            if (notice == null) {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
                return;
            }

            List<NoticeCategory> categories = categoryDAO.findAll();

            request.setAttribute("notice", notice);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/admin/edit-notice.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}