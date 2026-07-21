<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="auth-page-wrapper py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-5">
                    <div class="form-card">
                        <div class="text-center mb-4">
                            <i class="bi bi-mortarboard-fill fs-1" style="color: var(--primary-color);"></i>
                            <h3 class="mt-2">Welcome Back</h3>
                            <p class="text-muted">Login to access the departmental notice board</p>
                        </div>

                       

                        <c:if test="${param.registered == 'true'}">
                            <div class="alert alert-success alert-auto-dismiss">
                                <i class="bi bi-check-circle-fill me-2"></i>Registration successful. Please login.
                            </div>
                        </c:if>
                        <c:if test="${param.loggedout == 'true'}">
                            <div class="alert alert-success alert-auto-dismiss">
                                <i class="bi bi-check-circle-fill me-2"></i>You have been logged out successfully.
                            </div>
                        </c:if>
                        <c:if test="${param.sessionExpired == 'true'}">
                            <div class="alert alert-warning alert-auto-dismiss">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                Please login to access that page.
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="mb-3">
                                <label class="form-label">Username</label>
                                <input type="text" name="username" class="form-control"
                                       value="${username}" required autofocus>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label>
                                <input type="password" name="password" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-department-primary w-100 py-2">
                                <i class="bi bi-box-arrow-in-right me-1"></i>Login
                            </button>
                        </form>

                        <p class="text-center mt-4 mb-0">
                            Do not have an account yet?
                            <a href="${pageContext.request.contextPath}/register.jsp">Register here</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <jsp:include page="/includes/footer.jsp" />
    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>