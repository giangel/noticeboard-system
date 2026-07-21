<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.noticeboard.dao.UserDAO" %>
<%@ page import="com.noticeboard.model.Student" %>
<%@ page import="com.noticeboard.util.SessionUtil" %>
<%
    Integer profileUserId = SessionUtil.getUserId(request);
    if (profileUserId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    UserDAO profileUserDAO = new UserDAO();
    Student profileStudent = profileUserDAO.findStudentByUserId(profileUserId);
    request.setAttribute("profileStudent", profileStudent);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>My Profile - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="profile" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-student.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">My Profile</h2>

                <div class="card notice-card" style="max-width: 640px;">
                    <div class="card-body p-4">
                        <div class="text-center mb-4">
                            <i class="bi bi-person-circle" style="font-size: 4rem; color: var(--primary-color);"></i>
                            <h4 class="mt-2 mb-0">${profileStudent.fullName}</h4>
                            <p class="text-muted">${profileStudent.matricNumber}</p>
                        </div>

                        <table class="table table-borderless mb-0">
                            <tr>
                                <th class="text-muted" style="width: 40%;">Username</th>
                                <td>${profileStudent.username}</td>
                            </tr>
                            <tr>
                                <th class="text-muted">Email</th>
                                <td>${profileStudent.email}</td>
                            </tr>
                            <tr>
                                <th class="text-muted">Matric Number</th>
                                <td>${profileStudent.matricNumber}</td>
                            </tr>
                            <tr>
                                <th class="text-muted">Academic Level</th>
                                <td>${profileStudent.academicLevel}</td>
                            </tr>
                            <tr>
                                <th class="text-muted">Class Group</th>
                                <td>${not empty profileStudent.classGroup ? profileStudent.classGroup : 'Not set'}</td>
                            </tr>
                            <tr>
                                <th class="text-muted">Account Status</th>
                                <td>
                                    <span class="badge ${profileStudent.accountStatus == 'ACTIVE' ? 'badge-status-published' : 'badge-status-archived'}">
                                        ${profileStudent.accountStatus}
                                    </span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>