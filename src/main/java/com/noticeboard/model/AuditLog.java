package com.noticeboard.model;

import java.sql.Timestamp;

public class AuditLog {

    private int logId;
    private int userId;
    private String action;
    private String actionDetails;
    private Timestamp actionTime;

    // Field populated from a join with users, used for display only
    private String performedByName;

    public AuditLog() {
    }

    public AuditLog(int logId, int userId, String action, String actionDetails, Timestamp actionTime) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.actionDetails = actionDetails;
        this.actionTime = actionTime;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public String getPerformedByName() {
        return performedByName;
    }

    public void setPerformedByName(String performedByName) {
        this.performedByName = performedByName;
    }
}