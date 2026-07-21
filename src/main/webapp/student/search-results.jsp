<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Search Notices - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />
    <jsp:include page="/includes/toast-container.jsp" />

    <c:set var="activePage" value="search" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-student.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Search and Filter Notices</h2>

                <!-- FILTER PANEL -->
                <div class="card notice-card mb-4">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/search-notices" method="get">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label">Keyword</label>
                                    <input type="text" name="keyword" class="form-control"
                                           value="${keyword}" placeholder="Title or content">
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Category</label>
                                    <select name="categoryId" class="form-select">
                                        <option value="">All Categories</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryId}"
                                                ${categoryId == category.categoryId ? 'selected' : ''}>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Urgency</label>
                                    <select name="urgencyLevel" class="form-select">
                                        <option value="">Any</option>
                                        <option value="NORMAL" ${urgencyLevel == 'NORMAL' ? 'selected' : ''}>Normal</option>
                                        <option value="IMPORTANT" ${urgencyLevel == 'IMPORTANT' ? 'selected' : ''}>Important</option>
                                        <option value="URGENT" ${urgencyLevel == 'URGENT' ? 'selected' : ''}>Urgent</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Academic Level</label>
                                    <select name="academicLevel" class="form-select">
                                        <option value="">Any</option>
                                        <option value="ND1" ${academicLevel == 'ND1' ? 'selected' : ''}>ND1</option>
                                        <option value="ND2" ${academicLevel == 'ND2' ? 'selected' : ''}>ND2</option>
                                        <option value="HND1" ${academicLevel == 'HND1' ? 'selected' : ''}>HND1</option>
                                        <option value="HND2" ${academicLevel == 'HND2' ? 'selected' : ''}>HND2</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row g-3 mt-1">
                                <div class="col-md-4">
                                    <label class="form-label">Class Group</label>
                                    <input type="text" name="classGroup" class="form-control"
                                           value="${classGroup}" placeholder="e.g. ND2A">
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Sort Order</label>
                                    <select name="sortOrder" class="form-select">
                                        <option value="LATEST" ${sortOrder == 'LATEST' || empty sortOrder ? 'selected' : ''}>Latest First</option>
                                        <option value="OLDEST" ${sortOrder == 'OLDEST' ? 'selected' : ''}>Oldest First</option>
                                    </select>
                                </div>
                                <div class="col-md-5 d-flex align-items-end">
                                    <button type="submit" class="btn btn-department-primary me-2">
                                        <i class="bi bi-search me-1"></i>Search
                                    </button>
                                    <a href="${pageContext.request.contextPath}/search-notices" class="btn btn-outline-secondary">
                                        Clear Filters
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <p class="text-muted">${totalRecords} result(s) found</p>

                <c:choose>
                    <c:when test="${empty results}">
                        <div class="empty-state">
                            <i class="bi bi-search"></i>
                            <p>No notices matched your search criteria. Try adjusting your filters.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row g-3">
                            <c:forEach var="notice" items="${results}">
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

                        <c:if test="${totalPages > 1}">
                            <nav class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/search-notices?page=${pageNum}&keyword=${keyword}&categoryId=${categoryId}&urgencyLevel=${urgencyLevel}&academicLevel=${academicLevel}&classGroup=${classGroup}&sortOrder=${sortOrder}">
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