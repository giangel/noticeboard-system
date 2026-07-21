<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Audit Logs - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="auditLogs" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="section-heading mb-0">Audit Logs</h2>
                    <span class="text-muted">${totalRecords} entries</span>
                </div>

                <c:choose>
                    <c:when test="${empty logs}">
                        <div class="empty-state">
                            <i class="bi bi-clock-history"></i>
                            <p>No administrative activity has been recorded yet.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-department bg-white align-middle">
                                <thead>
                                    <tr>
                                        <th>Action</th>
                                        <th>Details</th>
                                        <th>Performed By</th>
                                        <th>Date and Time</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="log" items="${logs}">
                                        <tr>
                                            <td><span class="badge badge-category">${log.action}</span></td>
                                            <td>${log.actionDetails}</td>
                                            <td>${log.performedByName}</td>
                                            <td>${log.actionTime}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <c:if test="${totalPages > 1}">
                            <nav class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/admin/audit-logs?page=${pageNum}">
                                                ${pageNum}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>