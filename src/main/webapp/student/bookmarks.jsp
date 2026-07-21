<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.noticeboard.dao.NoticeBookmarkDAO" %>
<%@ page import="com.noticeboard.dao.UserDAO" %>
<%@ page import="com.noticeboard.model.NoticeBookmark" %>
<%@ page import="com.noticeboard.model.Student" %>
<%@ page import="com.noticeboard.util.SessionUtil" %>
<%
    Integer bookmarksUserId = SessionUtil.getUserId(request);
    if (bookmarksUserId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    UserDAO bookmarksUserDAO = new UserDAO();
    NoticeBookmarkDAO bookmarksDAO = new NoticeBookmarkDAO();

    Student bookmarksStudent = bookmarksUserDAO.findStudentByUserId(bookmarksUserId);
    List<NoticeBookmark> studentBookmarks = bookmarksStudent != null
            ? bookmarksDAO.findByStudentId(bookmarksStudent.getStudentId())
            : java.util.Collections.emptyList();

    request.setAttribute("studentBookmarks", studentBookmarks);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>My Bookmarks - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />
    <jsp:include page="/includes/toast-container.jsp" />

    <c:set var="activePage" value="bookmarks" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-student.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">My Bookmarked Notices</h2>

                <c:choose>
                    <c:when test="${empty studentBookmarks}">
                        <div class="empty-state">
                            <i class="bi bi-bookmark-star"></i>
                            <p>You have not saved any notices yet. Bookmark a notice to find it here later.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-department align-middle bg-white">
                                <thead>
                                    <tr>
                                        <th>Notice Title</th>
                                        <th>Urgency</th>
                                        <th>Status</th>
                                        <th>Saved On</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="bookmark" items="${studentBookmarks}">
                                        <tr>
                                            <td>${bookmark.noticeTitle}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${bookmark.urgencyLevel == 'URGENT'}">
                                                        <span class="badge badge-urgency-urgent">Urgent</span>
                                                    </c:when>
                                                    <c:when test="${bookmark.urgencyLevel == 'IMPORTANT'}">
                                                        <span class="badge badge-urgency-important">Important</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-urgency-normal">Normal</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${bookmark.status == 'PUBLISHED'}">
                                                        <span class="badge badge-status-published">Published</span>
                                                    </c:when>
                                                    <c:when test="${bookmark.status == 'ARCHIVED'}">
                                                        <span class="badge badge-status-archived">Archived</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-status-expired">Expired</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${bookmark.bookmarkedAt}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/notice-details?id=${bookmark.noticeId}"
                                                   class="btn btn-sm btn-department-primary">View</a>
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