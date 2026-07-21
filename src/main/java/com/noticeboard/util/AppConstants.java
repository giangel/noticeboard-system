package com.noticeboard.util;

public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    // Role names, must match the role_name values in the roles table
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STUDENT = "STUDENT";

    // Account status values, must match the account_status column in the users table
    public static final String ACCOUNT_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_INACTIVE = "INACTIVE";

    // Notice status values, must match the status column in the notices table
    public static final String NOTICE_STATUS_DRAFT = "DRAFT";
    public static final String NOTICE_STATUS_PUBLISHED = "PUBLISHED";
    public static final String NOTICE_STATUS_ARCHIVED = "ARCHIVED";
    public static final String NOTICE_STATUS_EXPIRED = "EXPIRED";

    // Urgency level values, must match the urgency_level column in the notices table
    public static final String URGENCY_NORMAL = "NORMAL";
    public static final String URGENCY_IMPORTANT = "IMPORTANT";
    public static final String URGENCY_URGENT = "URGENT";

    // Target audience values, must match the target_audience column in the notices table
    public static final String TARGET_ALL = "ALL";
    public static final String TARGET_LEVEL = "LEVEL";
    public static final String TARGET_CLASS = "CLASS";

    // Session attribute names, used consistently by SessionUtil and all Servlets
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USERNAME = "username";
    public static final String SESSION_FULL_NAME = "fullName";
    public static final String SESSION_ROLE = "role";
    public static final String SESSION_STUDENT_ID = "studentId";
    public static final String SESSION_ADMIN_ID = "adminId";

    // Pagination default values
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;
}