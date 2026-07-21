<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="col-lg-2 dashboard-sidebar">
    <ul class="nav flex-column px-2">
        <li class="nav-item">
            <a class="nav-link ${activePage == 'dashboard' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="bi bi-speedometer2"></i>Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'notices' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/notices">
                <i class="bi bi-file-earmark-text"></i>Manage Notices
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'createNotice' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/create-notice">
                <i class="bi bi-plus-square"></i>Create Notice
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'categories' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/categories">
                <i class="bi bi-tags"></i>Categories
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'users' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/users">
                <i class="bi bi-people"></i>Students
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'auditLogs' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/audit-logs">
                <i class="bi bi-clock-history"></i>Audit Logs
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${activePage == 'profile' ? 'active' : ''}"
               href="${pageContext.request.contextPath}/admin/profile.jsp">
                <i class="bi bi-person-gear"></i>Profile
            </a>
        </li>
    </ul>
</div>