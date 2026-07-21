<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin Dashboard - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="dashboard" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Admin Dashboard</h2>
                <p class="text-muted mb-4">Welcome back, ${sessionScope.fullName}</p>


                <!-- STATISTICS -->
                <div class="row g-3 mb-4">
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-primary">
                            <div class="stat-number">${totalNotices}</div>
                            <div class="stat-label">Total Notices</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-success">
                            <div class="stat-number">${publishedCount}</div>
                            <div class="stat-label">Published</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-warning">
                            <div class="stat-number">${draftCount}</div>
                            <div class="stat-label">Drafts</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-danger">
                            <div class="stat-number">${urgentCount}</div>
                            <div class="stat-label">Urgent Notices</div>
                        </div>
                    </div>
                </div>

                <div class="row g-3 mb-4">
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-dark">
                            <div class="stat-number">${archivedCount}</div>
                            <div class="stat-label">Archived</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-dark">
                            <div class="stat-number">${expiredCount}</div>
                            <div class="stat-label">Expired</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-primary">
                            <div class="stat-number">${publishedThisMonth}</div>
                            <div class="stat-label">Published This Month</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-success">
                            <div class="stat-number">${totalStudents}</div>
                            <div class="stat-label">Registered Students</div>
                        </div>
                    </div>
                </div>

                <div class="row g-4">
                    <!-- RECENT NOTICES -->
                    <div class="col-lg-6">
                        <h4 class="section-heading">Recent Notices</h4>
                        <c:choose>
                            <c:when test="${empty recentNotices}">
                                <div class="empty-state">
                                    <i class="bi bi-inbox"></i>
                                    <p>No published notices yet.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-department bg-white align-middle">
                                        <thead>
                                            <tr>
                                                <th>Title</th>
                                                <th>Urgency</th>
                                                <th>Published</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="notice" items="${recentNotices}">
                                                <tr>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/admin/edit-notice?id=${notice.noticeId}">
                                                            ${notice.title}
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${notice.urgencyLevel == 'URGENT'}">
                                                                <span class="badge badge-urgency-urgent">Urgent</span>
                                                            </c:when>
                                                            <c:when test="${notice.urgencyLevel == 'IMPORTANT'}">
                                                                <span class="badge badge-urgency-important">Important</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-urgency-normal">Normal</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${notice.formattedDatePublished}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- MOST VIEWED NOTICES -->
                    <div class="col-lg-6">
                        <h4 class="section-heading">Most Viewed Notices</h4>
                        <c:choose>
                            <c:when test="${empty mostViewedNotices}">
                                <div class="empty-state">
                                    <i class="bi bi-eye"></i>
                                    <p>No view data yet.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="table table-department bg-white align-middle">
                                        <thead>
                                            <tr>
                                                <th>Title</th>
                                                <th>Views</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="notice" items="${mostViewedNotices}">
                                                <tr>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/admin/edit-notice?id=${notice.noticeId}">
                                                            ${notice.title}
                                                        </a>
                                                    </td>
                                                    <td><span class="badge badge-category">${notice.viewCount}</span></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- RECENT ACTIVITY -->
                <h4 class="section-heading mt-3">Recent Administrative Activity</h4>
                <c:choose>
                    <c:when test="${empty recentActivity}">
                        <div class="empty-state">
                            <i class="bi bi-clock-history"></i>
                            <p>No administrative activity recorded yet.</p>
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
                                        <th>Time</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="log" items="${recentActivity}">
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
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>