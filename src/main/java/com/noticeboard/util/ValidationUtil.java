package com.noticeboard.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{4,50}$");

    private static final Pattern MATRIC_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9/-]{5,30}$");

    private ValidationUtil() {
        // Prevent instantiation
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        if (isNullOrEmpty(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidMatricNumber(String matricNumber) {
        if (isNullOrEmpty(matricNumber)) {
            return false;
        }
        return MATRIC_NUMBER_PATTERN.matcher(matricNumber.trim()).matches();
    }

    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) {
            return false;
        }
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }

    public static boolean isPositiveInteger(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }
        try {
            int number = Integer.parseInt(value.trim());
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String sanitizeForOutput(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    public static String trimOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }
}