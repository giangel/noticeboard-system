package com.noticeboard.model;

import java.sql.Timestamp;

public class Notice {

    private int noticeId;
    private String title;
    private String content;
    private int categoryId;
    private String urgencyLevel;
    private String targetAudience;
    private String academicLevel;
    private String classGroup;
    private int createdBy;
    private Timestamp dateCreated;
    private Timestamp datePublished;
    private Timestamp expiryDate;
    private String status;
    private int viewCount;

    // Fields populated from joins, used for display only
    private String categoryName;
    private String createdByName;
    private boolean readByCurrentStudent;
    private boolean bookmarkedByCurrentStudent;

    public Notice() {
    }

    public Notice(int noticeId, String title, String content, int categoryId, String urgencyLevel,
            String targetAudience, String academicLevel, String classGroup, int createdBy,
            Timestamp dateCreated, Timestamp datePublished, Timestamp expiryDate, String status,
            int viewCount) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.urgencyLevel = urgencyLevel;
        this.targetAudience = targetAudience;
        this.academicLevel = academicLevel;
        this.classGroup = classGroup;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.datePublished = datePublished;
        this.expiryDate = expiryDate;
        this.status = status;
        this.viewCount = viewCount;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Timestamp datePublished) {
        this.datePublished = datePublished;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public boolean isReadByCurrentStudent() {
        return readByCurrentStudent;
    }

    public void setReadByCurrentStudent(boolean readByCurrentStudent) {
        this.readByCurrentStudent = readByCurrentStudent;
    }

    public boolean isBookmarkedByCurrentStudent() {
        return bookmarkedByCurrentStudent;
    }

    public void setBookmarkedByCurrentStudent(boolean bookmarkedByCurrentStudent) {
        this.bookmarkedByCurrentStudent = bookmarkedByCurrentStudent;
    }

    public boolean isDraft() {
        return "DRAFT".equals(this.status);
    }

    public boolean isPublished() {
        return "PUBLISHED".equals(this.status);
    }

    public boolean isArchived() {
        return "ARCHIVED".equals(this.status);
    }

    public boolean isExpired() {
        return "EXPIRED".equals(this.status);
    }

    public boolean isUrgent() {
        return "URGENT".equals(this.urgencyLevel);
    }

    public boolean isImportant() {
        return "IMPORTANT".equals(this.urgencyLevel);
    }
    public String getFormattedDatePublished() {
        return com.noticeboard.util.DateUtil.formatShortDate(this.datePublished);
    }

    public String getFormattedExpiryDate() {
        return com.noticeboard.util.DateUtil.formatShortDate(this.expiryDate);
    }
}