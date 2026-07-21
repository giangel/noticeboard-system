package com.noticeboard.model;

import java.sql.Timestamp;

public class NoticeView {

    private int viewId;
    private int noticeId;
    private int studentId;
    private Timestamp viewedAt;

    public NoticeView() {
    }

    public NoticeView(int viewId, int noticeId, int studentId, Timestamp viewedAt) {
        this.viewId = viewId;
        this.noticeId = noticeId;
        this.studentId = studentId;
        this.viewedAt = viewedAt;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
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

    public Timestamp getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(Timestamp viewedAt) {
        this.viewedAt = viewedAt;
    }
}