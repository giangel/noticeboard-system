package com.noticeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.noticeboard.model.Administrator;
import com.noticeboard.model.Student;
import com.noticeboard.model.User;
import com.noticeboard.util.DBConnection;

public class UserDAO {

    // ============================================
    // USER AUTHENTICATION METHODS
    // ============================================

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT u.user_id, u.username, u.email, u.password_hash, u.full_name, "
                + "u.role_id, r.role_name, u.account_status, u.created_at "
                + "FROM users u JOIN roles r ON u.role_id = r.role_id "
                + "WHERE u.username = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUserRow(rs);
                }
            }
        }
        return null;
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT u.user_id, u.username, u.email, u.password_hash, u.full_name, "
                + "u.role_id, r.role_name, u.account_status, u.created_at "
                + "FROM users u JOIN roles r ON u.role_id = r.role_id "
                + "WHERE u.email = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUserRow(rs);
                }
            }
        }
        return null;
    }

    public User findById(int userId) throws SQLException {
        String sql = "SELECT u.user_id, u.username, u.email, u.password_hash, u.full_name, "
                + "u.role_id, r.role_name, u.account_status, u.created_at "
                + "FROM users u JOIN roles r ON u.role_id = r.role_id "
                + "WHERE u.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUserRow(rs);
                }
            }
        }
        return null;
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean matricNumberExists(String matricNumber) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE matric_number = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // ============================================
    // USER REGISTRATION METHODS
    // ============================================

    public int insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, full_name, role_id, account_status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getFullName());
            stmt.setInt(5, user.getRoleId());
            stmt.setString(6, user.getAccountStatus());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public int insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (user_id, matric_number, academic_level, class_group) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, student.getUserId());
            stmt.setString(2, student.getMatricNumber());
            stmt.setString(3, student.getAcademicLevel());
            stmt.setString(4, student.getClassGroup());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }

    public int insertAdministrator(Administrator admin) throws SQLException {
        String sql = "INSERT INTO administrators (user_id, staff_id, designation) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, admin.getUserId());
            stmt.setString(2, admin.getStaffId());
            stmt.setString(3, admin.getDesignation());

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
    // STUDENT AND ADMINISTRATOR LOOKUP METHODS
    // ============================================

    public Student findStudentByUserId(int userId) throws SQLException {
        String sql = "SELECT s.student_id, s.user_id, s.matric_number, s.academic_level, s.class_group, "
                + "u.full_name, u.email, u.username, u.account_status "
                + "FROM students s JOIN users u ON s.user_id = u.user_id "
                + "WHERE s.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapStudentRow(rs);
                }
            }
        }
        return null;
    }

    public Administrator findAdministratorByUserId(int userId) throws SQLException {
        String sql = "SELECT a.admin_id, a.user_id, a.staff_id, a.designation, "
                + "u.full_name, u.email, u.username, u.account_status "
                + "FROM administrators a JOIN users u ON a.user_id = u.user_id "
                + "WHERE a.user_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapAdministratorRow(rs);
                }
            }
        }
        return null;
    }

    public List<Student> findAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.student_id, s.user_id, s.matric_number, s.academic_level, s.class_group, "
                + "u.full_name, u.email, u.username, u.account_status "
                + "FROM students s JOIN users u ON s.user_id = u.user_id "
                + "ORDER BY u.full_name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudentRow(rs));
            }
        }
        return students;
    }

    public int countAllStudents() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM students";

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
    // ACCOUNT STATUS MANAGEMENT
    // ============================================

    public boolean updateAccountStatus(int userId, String newStatus) throws SQLException {
        String sql = "UPDATE users SET account_status = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePasswordHash(int userId, String newPasswordHash) throws SQLException {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPasswordHash);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        }
    }

    // ============================================
    // ROW MAPPING HELPERS
    // ============================================

    private User mapUserRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setRoleId(rs.getInt("role_id"));
        user.setRoleName(rs.getString("role_name"));
        user.setAccountStatus(rs.getString("account_status"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }

    private Student mapStudentRow(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setUserId(rs.getInt("user_id"));
        student.setMatricNumber(rs.getString("matric_number"));
        student.setAcademicLevel(rs.getString("academic_level"));
        student.setClassGroup(rs.getString("class_group"));
        student.setFullName(rs.getString("full_name"));
        student.setEmail(rs.getString("email"));
        student.setUsername(rs.getString("username"));
        student.setAccountStatus(rs.getString("account_status"));
        return student;
    }

    private Administrator mapAdministratorRow(ResultSet rs) throws SQLException {
        Administrator admin = new Administrator();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUserId(rs.getInt("user_id"));
        admin.setStaffId(rs.getString("staff_id"));
        admin.setDesignation(rs.getString("designation"));
        admin.setFullName(rs.getString("full_name"));
        admin.setEmail(rs.getString("email"));
        admin.setUsername(rs.getString("username"));
        admin.setAccountStatus(rs.getString("account_status"));
        return admin;
    }
}