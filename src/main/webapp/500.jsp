<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Server Error - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="container my-5">
        <div class="empty-state">
            <i class="bi bi-exclamation-octagon"></i>
            <h2 class="mb-2">500, Something Went Wrong</h2>
            <p class="mb-4">
                An unexpected server error occurred while processing your request. Please try
                again shortly. If the problem continues, contact the department.
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