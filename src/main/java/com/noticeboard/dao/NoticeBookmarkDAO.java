package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.noticeboard.model.NoticeBookmark;
import com.noticeboard.util.DBConnection;

public class NoticeBookmarkDAO {

    public boolean isBookmarked(int noticeId, int studentId) throws SQLException {
        String sql = "SELECT 1 FROM notice_bookmarks WHERE notice_id = ? AND student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);
            stmt.setInt(2, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int addBookmark(int noticeId, int studentId) throws SQLException {
        String sql = "INSERT INTO notice_bookmarks (notice_id, student_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, noticeId);
            stmt.setInt(2, studentId);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean removeBookmark(int noticeId, int studentId) throws SQLException {
        String sql = "DELETE FROM notice_bookmarks WHERE notice_id = ? AND student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);
            stmt.setInt(2, studentId);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<NoticeBookmark> findByStudentId(int studentId) throws SQLException {
        List<NoticeBookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT b.bookmark_id, b.notice_id, b.student_id, b.bookmarked_at, "
                + "n.title AS notice_title, n.urgency_level, n.status "
                + "FROM notice_bookmarks b JOIN notices n ON b.notice_id = n.notice_id "
                + "WHERE b.student_id = ? ORDER BY b.bookmarked_at DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookmarks.add(mapRow(rs));
                }
            }
        }
        return bookmarks;
    }

    public int countByStudentId(int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notice_bookmarks WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    private NoticeBookmark mapRow(ResultSet rs) throws SQLException {
        NoticeBookmark bookmark = new NoticeBookmark();
        bookmark.setBookmarkId(rs.getInt("bookmark_id"));
        bookmark.setNoticeId(rs.getInt("notice_id"));
        bookmark.setStudentId(rs.getInt("student_id"));
        bookmark.setBookmarkedAt(rs.getTimestamp("bookmarked_at"));
        bookmark.setNoticeTitle(rs.getString("notice_title"));
        bookmark.setUrgencyLevel(rs.getString("urgency_level"));
        bookmark.setStatus(rs.getString("status"));
        return bookmark;
    }
}