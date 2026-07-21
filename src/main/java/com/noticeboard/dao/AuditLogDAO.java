package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.noticeboard.model.AuditLog;
import com.noticeboard.util.DBConnection;

public class AuditLogDAO {

    public int insertLog(int userId, String action, String actionDetails) throws SQLException {
        String sql = "INSERT INTO audit_logs (user_id, action, action_details) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setString(2, action);
            stmt.setString(3, actionDetails);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public List<AuditLog> findRecent(int limit) throws SQLException {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT l.log_id, l.user_id, l.action, l.action_details, l.action_time, "
                + "u.full_name AS performed_by_name "
                + "FROM audit_logs l JOIN users u ON l.user_id = u.user_id "
                + "ORDER BY l.action_time DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapRow(rs));
                }
            }
        }
        return logs;
    }

    public List<AuditLog> findAllPaginated(int limit, int offset) throws SQLException {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT l.log_id, l.user_id, l.action, l.action_details, l.action_time, "
                + "u.full_name AS performed_by_name "
                + "FROM audit_logs l JOIN users u ON l.user_id = u.user_id "
                + "ORDER BY l.action_time DESC LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapRow(rs));
                }
            }
        }
        return logs;
    }

    public int countAllLogs() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM audit_logs";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    private AuditLog mapRow(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setLogId(rs.getInt("log_id"));
        log.setUserId(rs.getInt("user_id"));
        log.setAction(rs.getString("action"));
        log.setActionDetails(rs.getString("action_details"));
        log.setActionTime(rs.getTimestamp("action_time"));
        log.setPerformedByName(rs.getString("performed_by_name"));
        return log;
    }
}