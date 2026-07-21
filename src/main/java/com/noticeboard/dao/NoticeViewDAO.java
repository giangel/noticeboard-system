package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.noticeboard.util.DBConnection;

public class NoticeViewDAO {

    // Records a view, or updates the viewed_at timestamp if the student already viewed this notice
    public boolean recordView(int noticeId, int studentId) throws SQLException {
        String sql = "INSERT INTO notice_views (notice_id, student_id, viewed_at) VALUES (?, ?, ?) "
                + "ON CONFLICT (notice_id, student_id) DO UPDATE SET viewed_at = EXCLUDED.viewed_at";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);
            stmt.setInt(2, studentId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hasStudentViewedNotice(int noticeId, int studentId) throws SQLException {
        String sql = "SELECT 1 FROM notice_views WHERE notice_id = ? AND student_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noticeId);
            stmt.setInt(2, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countUnreadForStudent(String academicLevel, String classGroup, int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notices n "
                + "WHERE n.status = 'PUBLISHED' AND ("
                + "n.target_audience = 'ALL' "
                + "OR (n.target_audience = 'LEVEL' AND n.academic_level = ?) "
                + "OR (n.target_audience = 'CLASS' AND n.academic_level = ? AND n.class_group = ?)"
                + ") AND NOT EXISTS ("
                + "SELECT 1 FROM notice_views v WHERE v.notice_id = n.notice_id AND v.student_id = ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, academicLevel);
            stmt.setString(2, academicLevel);
            stmt.setString(3, classGroup);
            stmt.setInt(4, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int countTotalViews() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM notice_views";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}