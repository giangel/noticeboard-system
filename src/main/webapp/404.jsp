<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Page Not Found - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="container my-5">
        <div class="empty-state">
            <i class="bi bi-signpost-split"></i>
            <h2 class="mb-2">404, Page Not Found</h2>
            <p class="mb-4">
                The page you are looking for does not exist or may have been moved.
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