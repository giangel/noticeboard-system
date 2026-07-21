package com.noticeboard.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionUtil {

    private SessionUtil() {
        // Prevent instantiation
    }

    public static void createUserSession(HttpServletRequest request, int userId, String username,
            String fullName, String role) {
        HttpSession session = request.getSession(true);
        session.setAttribute(AppConstants.SESSION_USER_ID, userId);
        session.setAttribute(AppConstants.SESSION_USERNAME, username);
        session.setAttribute(AppConstants.SESSION_FULL_NAME, fullName);
        session.setAttribute(AppConstants.SESSION_ROLE, role);
    }

    public static void setStudentId(HttpServletRequest request, int studentId) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(AppConstants.SESSION_STUDENT_ID, studentId);
        }
    }

    public static void setAdminId(HttpServletRequest request, int adminId) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(AppConstants.SESSION_ADMIN_ID, adminId);
        }
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(AppConstants.SESSION_USER_ID) != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Object role = session.getAttribute(AppConstants.SESSION_ROLE);
        return role != null && AppConstants.ROLE_ADMIN.equals(role.toString());
    }

    public static boolean isStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Object role = session.getAttribute(AppConstants.SESSION_ROLE);
        return role != null && AppConstants.ROLE_STUDENT.equals(role.toString());
    }

    public static Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Integer) session.getAttribute(AppConstants.SESSION_USER_ID);
    }

    public static Integer getStudentId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Integer) session.getAttribute(AppConstants.SESSION_STUDENT_ID);
    }

    public static Integer getAdminId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Integer) session.getAttribute(AppConstants.SESSION_ADMIN_ID);
    }

    public static String getFullName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object fullName = session.getAttribute(AppConstants.SESSION_FULL_NAME);
        return fullName == null ? null : fullName.toString();
    }

    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}