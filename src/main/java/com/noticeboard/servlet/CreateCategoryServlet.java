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

@WebServlet("/admin/create-category")
public class CreateCategoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final NoticeCategoryDAO categoryDAO = new NoticeCategoryDAO();
    private final AuditLogDAO auditLogDAO = new AuditLogDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoryName = ValidationUtil.trimOrEmpty(request.getParameter("categoryName"));
        String categoryDescription = ValidationUtil.trimOrEmpty(request.getParameter("categoryDescription"));

        if (!ValidationUtil.isValidLength(categoryName, 3, 50)) {
            request.setAttribute("errorMessage", "Category name must be between 3 and 50 characters.");
            doGetCategories(request, response);
            return;
        }

        Integer userId = SessionUtil.getUserId(request);

        try {
            if (categoryDAO.categoryNameExists(categoryName)) {
                request.setAttribute("errorMessage", "This category already exists.");
                doGetCategories(request, response);
                return;
            }

            NoticeCategory category = new NoticeCategory();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);

            int newId = categoryDAO.insertCategory(category);

            if (newId != -1 && userId != null) {
                auditLogDAO.insertLog(userId, "CREATE_CATEGORY", "Created category: " + categoryName);
            }

            response.sendRedirect(request.getContextPath() + "/admin/categories");

        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }

    private void doGetCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("categories", categoryDAO.findAll());
            request.getRequestDispatcher("/admin/categories.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getRequestDispatcher("/500.jsp").forward(request, response);
        }
    }
}