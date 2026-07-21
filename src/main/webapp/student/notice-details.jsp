<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${notice.title} - CS Department Notice Board</title>
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
                <a href="${pageContext.request.contextPath}/student/notices" class="text-decoration-none mb-3 d-inline-block">
                    <i class="bi bi-arrow-left-short"></i> Back to All Notices
                </a>

                <div class="card notice-card">
                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-start flex-wrap gap-2 mb-3">
                            <div>
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
                                <span class="badge badge-category">${notice.categoryName}</span>
                                <c:choose>
                                    <c:when test="${notice.status == 'PUBLISHED'}">
                                        <span class="badge badge-status-published">Published</span>
                                    </c:when>
                                    <c:when test="${notice.status == 'ARCHIVED'}">
                                        <span class="badge badge-status-archived">Archived</span>
                                    </c:when>
                                    <c:when test="${notice.status == 'EXPIRED'}">
                                        <span class="badge badge-status-expired">Expired</span>
                                    </c:when>
                                </c:choose>
                            </div>
                            <button type="button" class="btn btn-outline-secondary btn-sm"
                                    data-bookmarked="${notice.bookmarkedByCurrentStudent}"
                                    onclick="toggleBookmark(${notice.noticeId}, this)">
                                <i class="bi ${notice.bookmarkedByCurrentStudent ? 'bi-bookmark-fill' : 'bi-bookmark'}"></i>
                                Save Notice
                            </button>
                        </div>

                        <h2 class="mb-3">${notice.title}</h2>

                        <div class="d-flex flex-wrap gap-4 text-muted small mb-4 pb-3 border-bottom">
                            <span><i class="bi bi-person-badge me-1"></i>Posted by ${notice.createdByName}</span>
                            <span><i class="bi bi-calendar3 me-1"></i>Published ${notice.formattedDatePublished}</span>
                            <c:if test="${not empty notice.expiryDate}">
                                <span><i class="bi bi-hourglass-split me-1"></i>Expires ${notice.formattedExpiryDate}</span>
                            </c:if>
                            <span><i class="bi bi-eye me-1"></i>${notice.viewCount} view(s)</span>
                            <c:if test="${notice.targetAudience != 'ALL'}">
                                <span><i class="bi bi-people me-1"></i>
                                    Targeted to ${notice.academicLevel}
                                    <c:if test="${not empty notice.classGroup}"> - ${notice.classGroup}</c:if>
                                </span>
                            </c:if>
                        </div>

                        <div class="notice-full-content" style="white-space: pre-wrap; line-height: 1.7;">
                            <c:out value="${notice.content}" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>