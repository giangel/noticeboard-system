package com.noticeboard.util;

public final class PaginationUtil {

    private PaginationUtil() {
        // Prevent instantiation
    }

    public static int calculateOffset(int pageNumber, int pageSize) {
        int safePageNumber = pageNumber < 1 ? 1 : pageNumber;
        int safePageSize = pageSize < 1 ? AppConstants.DEFAULT_PAGE_SIZE : pageSize;
        return (safePageNumber - 1) * safePageSize;
    }

    public static int calculateTotalPages(int totalRecords, int pageSize) {
        int safePageSize = pageSize < 1 ? AppConstants.DEFAULT_PAGE_SIZE : pageSize;
        if (totalRecords <= 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalRecords / safePageSize);
    }

    public static int parsePageNumber(String pageParam) {
        if (ValidationUtil.isNullOrEmpty(pageParam)) {
            return AppConstants.DEFAULT_PAGE_NUMBER;
        }
        try {
            int page = Integer.parseInt(pageParam.trim());
            return page < 1 ? AppConstants.DEFAULT_PAGE_NUMBER : page;
        } catch (NumberFormatException e) {
            return AppConstants.DEFAULT_PAGE_NUMBER;
        }
    }

    public static boolean hasPreviousPage(int currentPage) {
        return currentPage > 1;
    }

    public static boolean hasNextPage(int currentPage, int totalPages) {
        return currentPage < totalPages;
    }
}