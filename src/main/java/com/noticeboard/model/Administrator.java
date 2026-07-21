package com.noticeboard.model;

public class Administrator {

    private int adminId;
    private int userId;
    private String staffId;
    private String designation;

    // Fields populated from a join with the users table, used for display only
    private String fullName;
    private String email;
    private String username;
    private String accountStatus;

    public Administrator() {
    }

    public Administrator(int adminId, int userId, String staffId, String designation) {
        this.adminId = adminId;
        this.userId = userId;
        this.staffId = staffId;
        this.designation = designation;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}