<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Access Denied - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="container my-5">
        <div class="empty-state">
            <i class="bi bi-shield-lock"></i>
            <h2 class="mb-2">Access Denied</h2>
            <p class="mb-4">
                You do not have permission to access this section of the notice board.
                This area is restricted to a different account role.
            </p>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-department-primary">
                <i class="bi bi-house-door me-1"></i>Return to Homepage
            </a>
        </div>
    </section>

    <jsp:include page="/includes/footer.jsp" />
    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>