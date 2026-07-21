package com.noticeboard.model;

public class Student {

    private int studentId;
    private int userId;
    private String matricNumber;
    private String academicLevel;
    private String classGroup;

    // Fields populated from a join with the users table, used for display only
    private String fullName;
    private String email;
    private String username;
    private String accountStatus;

    public Student() {
    }

    public Student(int studentId, int userId, String matricNumber, String academicLevel, String classGroup) {
        this.studentId = studentId;
        this.userId = userId;
        this.matricNumber = matricNumber;
        this.academicLevel = academicLevel;
        this.classGroup = classGroup;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
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