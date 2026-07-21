<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>All Notices - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />
    <jsp:include page="/includes/toast-container.jsp" />

    <c:set var="activePage" value="notices" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-student.jsp" />

            <div class="col-lg-10 dashboard-content">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="section-heading mb-0">All Notices</h2>
                    <span class="text-muted">${totalRecords} notice(s) found</span>
                </div>

                

                <c:choose>
                    <c:when test="${empty notices}">
                        <div class="empty-state">
                            <i class="bi bi-inbox"></i>
                            <p>No notices are currently available for your level or class.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row g-3">
                            <c:forEach var="notice" items="${notices}">
                                <div class="col-md-4">
                                    <div class="card notice-card">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between align-items-start">
                                                <div>
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
                                                </div>
                                                <button type="button" class="btn btn-sm btn-link p-0"
                                                        data-bookmarked="false"
                                                        onclick="toggleBookmark(${notice.noticeId}, this)">
                                                    <i class="bi bi-bookmark fs-5"></i>
                                                </button>
                                            </div>
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

                        <!-- PAGINATION -->
                        <c:if test="${totalPages > 1}">
                            <nav class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/student/notices?page=${pageNum}">
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