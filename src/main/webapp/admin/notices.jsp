<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage Notices - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="notices" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                    <h2 class="section-heading mb-0">Manage Notices</h2>
                    <a href="${pageContext.request.contextPath}/admin/create-notice" class="btn btn-department-primary">
                        <i class="bi bi-plus-square me-1"></i>Create New Notice
                    </a>
                </div>


                <!-- FILTER BAR -->
                <div class="card notice-card mb-4">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/notices" method="get" class="row g-3">
                            <div class="col-md-6">
                                <input type="text" name="keyword" class="form-control"
                                       placeholder="Search by title or content" value="${keyword}">
                            </div>
                            <div class="col-md-3">
                                <select name="status" class="form-select">
                                    <option value="">All Statuses</option>
                                    <option value="DRAFT" ${statusFilter == 'DRAFT' ? 'selected' : ''}>Draft</option>
                                    <option value="PUBLISHED" ${statusFilter == 'PUBLISHED' ? 'selected' : ''}>Published</option>
                                    <option value="ARCHIVED" ${statusFilter == 'ARCHIVED' ? 'selected' : ''}>Archived</option>
                                    <option value="EXPIRED" ${statusFilter == 'EXPIRED' ? 'selected' : ''}>Expired</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <button type="submit" class="btn btn-department-primary w-100">
                                    <i class="bi bi-funnel me-1"></i>Apply Filter
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <p class="text-muted">${totalRecords} notice(s) found</p>

                <c:choose>
                    <c:when test="${empty notices}">
                        <div class="empty-state">
                            <i class="bi bi-file-earmark-text"></i>
                            <p>No notices match your criteria.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-department bg-white align-middle">
                                <thead>
                                    <tr>
                                        <th>Title</th>
                                        <th>Category</th>
                                        <th>Urgency</th>
                                        <th>Status</th>
                                        <th>Created</th>
                                        <th>Views</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="notice" items="${notices}">
                                        <tr>
                                            <td>${notice.title}</td>
                                            <td><span class="badge badge-category">${notice.categoryName}</span></td>
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
                                            <td>
                                                <c:choose>
                                                    <c:when test="${notice.status == 'DRAFT'}">
                                                        <span class="badge badge-status-draft">Draft</span>
                                                    </c:when>
                                                    <c:when test="${notice.status == 'PUBLISHED'}">
                                                        <span class="badge badge-status-published">Published</span>
                                                    </c:when>
                                                    <c:when test="${notice.status == 'ARCHIVED'}">
                                                        <span class="badge badge-status-archived">Archived</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-status-expired">Expired</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${notice.formattedDatePublished}</td>
                                            <td>${notice.viewCount}</td>
                                            <td>
                                                <div class="d-flex flex-wrap gap-1">
                                                    <a href="${pageContext.request.contextPath}/admin/edit-notice?id=${notice.noticeId}"
                                                       class="btn btn-sm btn-outline-primary" title="Edit">
                                                        <i class="bi bi-pencil-square"></i>
                                                    </a>

                                                    <c:if test="${notice.status == 'DRAFT'}">
                                                        <form action="${pageContext.request.contextPath}/admin/publish-notice" method="post" class="d-inline">
                                                            <input type="hidden" name="id" value="${notice.noticeId}">
                                                            <button type="submit" class="btn btn-sm btn-outline-success" title="Publish">
                                                                <i class="bi bi-send-check"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>

                                                    <c:if test="${notice.status == 'PUBLISHED'}">
                                                        <form action="${pageContext.request.contextPath}/admin/archive-notice" method="post" class="d-inline"
                                                              onsubmit="return confirmArchive();">
                                                            <input type="hidden" name="id" value="${notice.noticeId}">
                                                            <button type="submit" class="btn btn-sm btn-outline-warning" title="Archive">
                                                                <i class="bi bi-archive"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>

                                                    <c:if test="${notice.status == 'ARCHIVED'}">
                                                        <form action="${pageContext.request.contextPath}/admin/restore-notice" method="post" class="d-inline">
                                                            <input type="hidden" name="id" value="${notice.noticeId}">
                                                            <button type="submit" class="btn btn-sm btn-outline-success" title="Restore">
                                                                <i class="bi bi-arrow-counterclockwise"></i>
                                                            </button>
                                                        </form>
                                                    </c:if>

                                                    <form action="${pageContext.request.contextPath}/admin/delete-notice" method="post" class="d-inline"
                                                          onsubmit="return confirmDelete('Are you sure you want to permanently delete this notice?');">
                                                        <input type="hidden" name="id" value="${notice.noticeId}">
                                                        <button type="submit" class="btn btn-sm btn-outline-danger" title="Delete">
                                                            <i class="bi bi-trash"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
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
                                               href="${pageContext.request.contextPath}/admin/notices?page=${pageNum}&keyword=${keyword}&status=${statusFilter}">
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