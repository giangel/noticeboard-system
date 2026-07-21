package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.noticeboard.model.NoticeCategory;
import com.noticeboard.util.DBConnection;

public class NoticeCategoryDAO {

    public List<NoticeCategory> findAll() throws SQLException {
        List<NoticeCategory> categories = new ArrayList<>();
        String sql = "SELECT category_id, category_name, category_description "
                + "FROM notice_categories ORDER BY category_name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        }
        return categories;
    }

    public NoticeCategory findById(int categoryId) throws SQLException {
        String sql = "SELECT category_id, category_name, category_description "
                + "FROM notice_categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public boolean categoryNameExists(String categoryName) throws SQLException {
        String sql = "SELECT 1 FROM notice_categories WHERE category_name = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoryName);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int insertCategory(NoticeCategory category) throws SQLException {
        String sql = "INSERT INTO notice_categories (category_name, category_description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean updateCategory(NoticeCategory category) throws SQLException {
        String sql = "UPDATE notice_categories SET category_name = ?, category_description = ? "
                + "WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getCategoryDescription());
            stmt.setInt(3, category.getCategoryId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM notice_categories WHERE category_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isCategoryInUse(int categoryId) throws SQLException {
        String sql = "SELECT 1 FROM notices WHERE category_id = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private NoticeCategory mapRow(ResultSet rs) throws SQLException {
        NoticeCategory category = new NoticeCategory();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setCategoryDescription(rs.getString("category_description"));
        return category;
    }
}