<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Student Dashboard - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />
    <jsp:include page="/includes/toast-container.jsp" />

    <c:set var="activePage" value="dashboard" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-student.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Welcome, ${student.fullName}</h2>
                <p class="text-muted mb-4">
                    ${student.matricNumber} &bull; ${student.academicLevel}
                    <c:if test="${not empty student.classGroup}"> &bull; ${student.classGroup}</c:if>
                </p>

               

                <!-- STATISTICS -->
                <div class="row g-3 mb-4">
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-primary">
                            <div class="stat-number">${totalVisible}</div>
                            <div class="stat-label">Available Notices</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-warning">
                            <div class="stat-number">${unreadCount}</div>
                            <div class="stat-label">Unread Notices</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-danger">
                            <div class="stat-number">${fn:length(urgentNotices)}</div>
                            <div class="stat-label">Urgent Notices</div>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="stat-card stat-card-dark">
                            <div class="stat-number">${bookmarkCount}</div>
                            <div class="stat-label">Saved Notices</div>
                        </div>
                    </div>
                </div>

                <!-- URGENT NOTICES -->
                <c:if test="${not empty urgentNotices}">
                    <h4 class="section-heading">Urgent Notices</h4>
                    <div class="row g-3 mb-4">
                        <c:forEach var="notice" items="${urgentNotices}">
                            <div class="col-md-4">
                                <div class="card notice-card">
                                    <div class="card-body">
                                        <span class="badge badge-urgency-urgent mb-2">Urgent</span>
                                        <span class="badge badge-category mb-2">${notice.categoryName}</span>
                                        <h5 class="card-title">${notice.title}</h5>
                                        <p class="notice-preview"><c:out value="${notice.content}" /></p>
                                        <p class="small text-muted mb-2">
                                            <i class="bi bi-calendar3 me-1"></i>${notice.formattedDatePublished}
                                        </p>
                                        <a href="${pageContext.request.contextPath}/notice-details?id=${notice.noticeId}"
                                           class="btn btn-sm btn-department-primary">View Details</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <!-- RECENT NOTICES -->
                <h4 class="section-heading">Recent Notices</h4>
                <c:choose>
                    <c:when test="${empty recentNotices}">
                        <div class="empty-state">
                            <i class="bi bi-inbox"></i>
                            <p>No notices are currently available for your level or class.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row g-3">
                            <c:forEach var="notice" items="${recentNotices}">
                                <div class="col-md-4">
                                    <div class="card notice-card">
                                        <div class="card-body">
                                            <c:choose>
                                                <c:when test="${notice.urgencyLevel == 'URGENT'}">
                                                    <span class="badge badge-urgency-urgent mb-2">Urgent</span>
                                                </c:when>
                                                <c:when test="${notice.urgencyLevel == 'IMPORTANT'}">
                                                    <span class="badge badge-urgency-important mb-2">Important</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-urgency-normal mb-2">Normal</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="badge badge-category mb-2">${notice.categoryName}</span>
                                            <h5 class="card-title">${notice.title}</h5>
                                            <p class="notice-preview"><c:out value="${notice.content}" /></p>
                                            <p class="small text-muted mb-2">
                                                <i class="bi bi-calendar3 me-1"></i>${notice.formattedDatePublished}
                                            </p>
                                            <a href="${pageContext.request.contextPath}/notice-details?id=${notice.noticeId}"
                                               class="btn btn-sm btn-department-primary">View Details</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="text-center mt-4">
                            <a href="${pageContext.request.contextPath}/student/notices" class="btn btn-outline-secondary">
                                View All Notices <i class="bi bi-arrow-right-short"></i>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>