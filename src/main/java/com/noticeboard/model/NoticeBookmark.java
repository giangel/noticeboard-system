package com.noticeboard.model;

import java.sql.Timestamp;

public class NoticeBookmark {

    private int bookmarkId;
    private int noticeId;
    private int studentId;
    private Timestamp bookmarkedAt;

    // Fields populated from a join with notices, used for display on the bookmarks page
    private String noticeTitle;
    private String urgencyLevel;
    private String status;

    public NoticeBookmark() {
    }

    public NoticeBookmark(int bookmarkId, int noticeId, int studentId, Timestamp bookmarkedAt) {
        this.bookmarkId = bookmarkId;
        this.noticeId = noticeId;
        this.studentId = studentId;
        this.bookmarkedAt = bookmarkedAt;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Timestamp getBookmarkedAt() {
        return bookmarkedAt;
    }

    public void setBookmarkedAt(Timestamp bookmarkedAt) {
        this.bookmarkedAt = bookmarkedAt;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}