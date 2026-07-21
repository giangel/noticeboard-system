<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Students - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="users" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Manage Students</h2>


                <c:choose>
                    <c:when test="${empty students}">
                        <div class="empty-state">
                            <i class="bi bi-people"></i>
                            <p>No students have registered yet.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-department bg-white align-middle">
                                <thead>
                                    <tr>
                                        <th>Full Name</th>
                                        <th>Matric Number</th>
                                        <th>Email</th>
                                        <th>Academic Level</th>
                                        <th>Class Group</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="student" items="${students}">
                                        <tr>
                                            <td>${student.fullName}</td>
                                            <td>${student.matricNumber}</td>
                                            <td>${student.email}</td>
                                            <td>${student.academicLevel}</td>
                                            <td>${not empty student.classGroup ? student.classGroup : 'Not set'}</td>
                                            <td>
                                                <span class="badge ${student.accountStatus == 'ACTIVE' ? 'badge-status-published' : 'badge-status-archived'}">
                                                    ${student.accountStatus}
                                                </span>
                                            </td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/admin/update-user-status" method="post" class="d-inline">
                                                    <input type="hidden" name="userId" value="${student.userId}">
                                                    <c:choose>
                                                        <c:when test="${student.accountStatus == 'ACTIVE'}">
                                                            <input type="hidden" name="newStatus" value="INACTIVE">
                                                            <button type="submit" class="btn btn-sm btn-outline-danger"
                                                                    onclick="return confirm('Deactivate this student account?');">
                                                                Deactivate
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="hidden" name="newStatus" value="ACTIVE">
                                                            <button type="submit" class="btn btn-sm btn-outline-success">
                                                                Activate
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>