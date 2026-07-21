package com.noticeboard.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");

    private static final DateTimeFormatter SHORT_DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy");

    private DateUtil() {
        // Prevent instantiation
    }

    public static String formatForDisplay(Timestamp timestamp) {
        if (timestamp == null) {
            return "Not set";
        }
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        return dateTime.format(DISPLAY_FORMAT);
    }

    public static String formatShortDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "Not set";
        }
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        return dateTime.format(SHORT_DISPLAY_FORMAT);
    }

    public static boolean isExpired(Timestamp expiryDate) {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.before(Timestamp.valueOf(LocalDateTime.now()));
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Timestamp addDaysToCurrentTimestamp(int days) {
        LocalDateTime future = LocalDateTime.now().plusDays(days);
        return Timestamp.valueOf(future);
    }

    public static Timestamp parseExpiryDate(String dateString) {
        if (ValidationUtil.isNullOrEmpty(dateString)) {
            return null;
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateString + "T23:59:00");
            return Timestamp.valueOf(dateTime);
        } catch (Exception e) {
            return null;
        }
    }
}