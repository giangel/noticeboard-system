package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.noticeboard.dao.AuditLogDAO;
import com.noticeboard.dao.NoticeCategoryDAO;
import com.noticeboard.model.NoticeCategory;
import com.noticeboard.util.SessionUtil;
import com.noticeboard.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/delete-category")
public class DeleteCategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (!ValidationUtil.isPositiveInteger(idParam)) {
            response.sendRedirect(request.getContextPath() + "/admin/categories");
            return;
        }

        int categoryId = Integer.parseInt(idParam);
        Integer userId = SessionUtil.getUserId(request);

        try {
            if (categoryDAO.isCategoryInUse(categoryId)) {
                request.setAttribute("errorMessage",
                        "This category cannot be deleted because it is currently assigned to one or more notices.");
                List<NoticeCategory> categories = categoryDAO.findAll();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/admin/categories.jsp").forward(request, response);
                return;
            }

            NoticeCategory category = categoryDAO.findById(categoryId);
            String name = category != null ? category.getCategoryName() : "Category ID " + categoryId;

            boolean deleted = categoryDAO.deleteCategory(categoryId);

            if (deleted && userId != null) {
                auditLogDAO.insertLog(userId, "DELETE_CATEGORY", "Deleted category: " + name);
            }

            response.sendRedirect(request.getContextPath() + "/admin/categories");

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