<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="col-lg-2 dashboard-sidebar">
    <ul class="nav flex-column px-2">
        <li class="nav-item">
            <a class="nav-link ${activePage == 'dashboard' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/student/dashboard">
                <i class="bi bi-speedometer2"></i>Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'notices' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/student/notices">
                <i class="bi bi-file-earmark-text"></i>All Notices
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'search' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/search-notices">
                <i class="bi bi-search"></i>Search Notices
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'bookmarks' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/student/bookmarks.jsp">
                <i class="bi bi-bookmark-star"></i>Bookmarks
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'profile' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/student/profile.jsp">
                <i class="bi bi-person-gear"></i>Profile
            </a>
        </li>
    </ul>
</div>