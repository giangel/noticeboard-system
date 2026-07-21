package com.noticeboard.servlet;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/admin/update-category")
public class UpdateCategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryIdParam = request.getParameter("categoryId");
        String categoryName = ValidationUtil.trimOrEmpty(request.getParameter("categoryName"));
        String categoryDescription = ValidationUtil.trimOrEmpty(request.getParameter("categoryDescription"));

        if (!ValidationUtil.isPositiveInteger(categoryIdParam) || !ValidationUtil.isValidLength(categoryName, 3, 50)) {
            response.sendRedirect(request.getContextPath() + "/admin/categories");
            return;
        }

        Integer userId = SessionUtil.getUserId(request);

        try {
            NoticeCategory category = new NoticeCategory();
            category.setCategoryId(Integer.parseInt(categoryIdParam));
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);

            boolean updated = categoryDAO.updateCategory(category);

            if (updated && userId != null) {
                auditLogDAO.insertLog(userId, "UPDATE_CATEGORY", "Updated category: " + categoryName);
            }

            response.sendRedirect(request.getContextPath() + "/admin/categories");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}