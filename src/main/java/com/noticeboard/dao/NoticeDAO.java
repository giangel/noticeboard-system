package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.noticeboard.model.Notice;
import com.noticeboard.util.DBConnection;

public class NoticeDAO {

    // Base SELECT clause reused by most query methods, joins category name and creator name
    private static final String BASE_SELECT =
            "SELECT n.notice_id, n.title, n.content, n.category_id, n.urgency_level, "
            + "n.target_audience, n.academic_level, n.class_group, n.created_by, "
            + "n.date_created, n.date_published, n.expiry_date, n.status, n.view_count, "
            + "c.category_name, u.full_name AS created_by_name "
            + "FROM notices n "
            + "JOIN notice_categories c ON n.category_id = c.category_id "
            + "JOIN users u ON n.created_by = u.user_id ";

    // ============================================
    // CREATE
    // ============================================

    public int insertNotice(Notice notice) throws SQLException {
        String sql = "INSERT INTO notices (title, content, category_id, urgency_level, target_audience, "
                + "academic_level, class_group, created_by, date_published, expiry_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, notice.getTitle());
            stmt.setString(2, notice.getContent());
            stmt.setInt(3, notice.getCategoryId());
            stmt.setString(4, notice.getUrgencyLevel());
            stmt.setString(5, notice.getTargetAudience());
            stmt.setString(6, notice.getAcademicLevel());
            stmt.setString(7, notice.getClassGroup());
            stmt.setInt(8, notice.getCreatedBy());
            stmt.setTimestamp(9, notice.getDatePublished());
            stmt.setTimestamp(10, notice.getExpiryDate());
            stmt.setString(11, notice.getStatus());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    // ============================================
    // READ
    // ============================================

    public Notice findById(int noticeId) throws SQLException {
        String sql = BASE_SELECT + "WHERE n.notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<Notice> findByStatus(String status, int limit, int offset) throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE n.status = ? ORDER BY n.date_created DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public List<Notice> findLatestPublished(int limit) throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE n.status = 'PUBLISHED' ORDER BY n.date_published DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public List<Notice> findByUrgency(String urgencyLevel, int limit) throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE n.status = 'PUBLISHED' AND n.urgency_level = ? "
                + "ORDER BY n.date_published DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, urgencyLevel);
            stmt.setInt(2, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public List<Notice> findMostViewed(int limit) throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE n.status = 'PUBLISHED' ORDER BY n.view_count DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    // Retrieves published notices visible to a specific student, based on target audience rules
    public List<Notice> findVisibleToStudent(String academicLevel, String classGroup, int limit, int offset)
            throws SQLException {
        List<Notice> notices = new ArrayList<>();
        String sql = BASE_SELECT
                + "WHERE n.status = 'PUBLISHED' AND ("
                + "n.target_audience = 'ALL' "
                + "OR (n.target_audience = 'LEVEL' AND n.academic_level = ?) "
                + "OR (n.target_audience = 'CLASS' AND n.academic_level = ? AND n.class_group = ?)"
                + ") ORDER BY n.date_published DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, academicLevel);
            stmt.setString(2, academicLevel);
            stmt.setString(3, classGroup);
            stmt.setInt(4, limit);
            stmt.setInt(5, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public int countVisibleToStudent(String academicLevel, String classGroup) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices n "
                + "WHERE n.status = 'PUBLISHED' AND ("
                + "n.target_audience = 'ALL' "
                + "OR (n.target_audience = 'LEVEL' AND n.academic_level = ?) "
                + "OR (n.target_audience = 'CLASS' AND n.academic_level = ? AND n.class_group = ?)"
                + ")";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, academicLevel);
            stmt.setString(2, academicLevel);
            stmt.setString(3, classGroup);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    // ============================================
    // SEARCH AND FILTER
    // ============================================

    public List<Notice> searchAndFilter(String keyword, Integer categoryId, String urgencyLevel,
            String academicLevel, String classGroup, String sortOrder, int limit, int offset)
            throws SQLException {

        List<Notice> notices = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT);
        sql.append("WHERE n.status = 'PUBLISHED' ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (n.title ILIKE ? OR n.content ILIKE ?) ");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (categoryId != null && categoryId > 0) {
            sql.append("AND n.category_id = ? ");
            params.add(categoryId);
        }

        if (urgencyLevel != null && !urgencyLevel.trim().isEmpty()) {
            sql.append("AND n.urgency_level = ? ");
            params.add(urgencyLevel);
        }

        if (academicLevel != null && !academicLevel.trim().isEmpty()) {
            sql.append("AND (n.academic_level = ? OR n.target_audience = 'ALL') ");
            params.add(academicLevel);
        }

        if (classGroup != null && !classGroup.trim().isEmpty()) {
            sql.append("AND (n.class_group = ? OR n.target_audience IN ('ALL', 'LEVEL')) ");
            params.add(classGroup);
        }

        if ("OLDEST".equalsIgnoreCase(sortOrder)) {
            sql.append("ORDER BY n.date_published ASC ");
        } else {
            sql.append("ORDER BY n.date_published DESC ");
        }

        sql.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public int countSearchAndFilter(String keyword, Integer categoryId, String urgencyLevel,
            String academicLevel, String classGroup) throws SQLException {

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM notices n WHERE n.status = 'PUBLISHED' ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (n.title ILIKE ? OR n.content ILIKE ?) ");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (categoryId != null && categoryId > 0) {
            sql.append("AND n.category_id = ? ");
            params.add(categoryId);
        }

        if (urgencyLevel != null && !urgencyLevel.trim().isEmpty()) {
            sql.append("AND n.urgency_level = ? ");
            params.add(urgencyLevel);
        }

        if (academicLevel != null && !academicLevel.trim().isEmpty()) {
            sql.append("AND (n.academic_level = ? OR n.target_audience = 'ALL') ");
            params.add(academicLevel);
        }

        if (classGroup != null && !classGroup.trim().isEmpty()) {
            sql.append("AND (n.class_group = ? OR n.target_audience IN ('ALL', 'LEVEL')) ");
            params.add(classGroup);
        }

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    // Used by the admin notice management page, searches across all statuses
    public List<Notice> searchAdminNotices(String keyword, String statusFilter, int limit, int offset)
            throws SQLException {
        List<Notice> notices = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BASE_SELECT);
        sql.append("WHERE 1 = 1 ");

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (n.title ILIKE ? OR n.content ILIKE ?) ");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (statusFilter != null && !statusFilter.trim().isEmpty()) {
            sql.append("AND n.status = ? ");
            params.add(statusFilter);
        }

        sql.append("ORDER BY n.date_created DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notices.add(mapRow(rs));
                }
            }
        }
        return notices;
    }

    public int countAdminNotices(String keyword, String statusFilter) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS total FROM notices n WHERE 1 = 1 ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (n.title ILIKE ? OR n.content ILIKE ?) ");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        if (statusFilter != null && !statusFilter.trim().isEmpty()) {
            sql.append("AND n.status = ? ");
            params.add(statusFilter);
        }

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    // ============================================
    // UPDATE
    // ============================================

    public boolean updateNotice(Notice notice) throws SQLException {
        String sql = "UPDATE notices SET title = ?, content = ?, category_id = ?, urgency_level = ?, "
                + "target_audience = ?, academic_level = ?, class_group = ?, expiry_date = ? "
                + "WHERE notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, notice.getTitle());
            stmt.setString(2, notice.getContent());
            stmt.setInt(3, notice.getCategoryId());
            stmt.setString(4, notice.getUrgencyLevel());
            stmt.setString(5, notice.getTargetAudience());
            stmt.setString(6, notice.getAcademicLevel());
            stmt.setString(7, notice.getClassGroup());
            stmt.setTimestamp(8, notice.getExpiryDate());
            stmt.setInt(9, notice.getNoticeId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int noticeId, String newStatus) throws SQLException {
        String sql = "UPDATE notices SET status = ? WHERE notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, noticeId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean publishNotice(int noticeId) throws SQLException {
        String sql = "UPDATE notices SET status = 'PUBLISHED', date_published = ? WHERE notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, noticeId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean archiveNotice(int noticeId) throws SQLException {
        return updateStatus(noticeId, "ARCHIVED");
    }

    public boolean restoreNotice(int noticeId) throws SQLException {
        return updateStatus(noticeId, "PUBLISHED");
    }

    public boolean incrementViewCount(int noticeId) throws SQLException {
        String sql = "UPDATE notices SET view_count = view_count + 1 WHERE notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);

            return stmt.executeUpdate() > 0;
        }
    }

    // Marks all published notices whose expiry_date has passed as EXPIRED, can be called on dashboard load
    public int markExpiredNotices() throws SQLException {
        String sql = "UPDATE notices SET status = 'EXPIRED' "
                + "WHERE status = 'PUBLISHED' AND expiry_date IS NOT NULL AND expiry_date < ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

            return stmt.executeUpdate();
        }
    }

    // ============================================
    // DELETE
    // ============================================

    public boolean deleteNotice(int noticeId) throws SQLException {
        String sql = "DELETE FROM notices WHERE notice_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);

            return stmt.executeUpdate() > 0;
        }
    }

    // ============================================
    // STATISTICS FOR ADMIN DASHBOARD
    // ============================================

    public int countByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices WHERE status = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int countByUrgency(String urgencyLevel) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices WHERE urgency_level = ? AND status = 'PUBLISHED'";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, urgencyLevel);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int countTotalNotices() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public int countPublishedThisMonth() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices "
                + "WHERE status = 'PUBLISHED' "
                + "AND EXTRACT(MONTH FROM date_published) = EXTRACT(MONTH FROM CURRENT_DATE) "
                + "AND EXTRACT(YEAR FROM date_published) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // ============================================
    // ROW MAPPING HELPER
    // ============================================

    private Notice mapRow(ResultSet rs) throws SQLException {
        Notice notice = new Notice();
        notice.setNoticeId(rs.getInt("notice_id"));
        notice.setTitle(rs.getString("title"));
        notice.setContent(rs.getString("content"));
        notice.setCategoryId(rs.getInt("category_id"));
        notice.setUrgencyLevel(rs.getString("urgency_level"));
        notice.setTargetAudience(rs.getString("target_audience"));
        notice.setAcademicLevel(rs.getString("academic_level"));
        notice.setClassGroup(rs.getString("class_group"));
        notice.setCreatedBy(rs.getInt("created_by"));
        notice.setDateCreated(rs.getTimestamp("date_created"));
        notice.setDatePublished(rs.getTimestamp("date_published"));
        notice.setExpiryDate(rs.getTimestamp("expiry_date"));
        notice.setStatus(rs.getString("status"));
        notice.setViewCount(rs.getInt("view_count"));
        notice.setCategoryName(rs.getString("category_name"));
        notice.setCreatedByName(rs.getString("created_by_name"));
        return notice;
    }
}