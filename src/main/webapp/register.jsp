<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="auth-page-wrapper py-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="form-card">
                        <div class="text-center mb-4">
                            <i class="bi bi-person-plus-fill fs-1" style="color: var(--primary-color);"></i>
                            <h3 class="mt-2">Create Student Account</h3>
                            <p class="text-muted">Register to access the departmental notice board</p>
                        </div>

                        

                        <form action="${pageContext.request.contextPath}/register" method="post">
                            <div class="mb-3">
                                <label class="form-label">Full Name</label>
                                <input type="text" name="fullName" class="form-control"
                                       value="${fullName}" required>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Username</label>
                                    <input type="text" name="username" class="form-control"
                                           value="${username}" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control"
                                           value="${email}" required>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Password</label>
                                    <input type="password" name="password" class="form-control" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Confirm Password</label>
                                    <input type="password" name="confirmPassword" class="form-control" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Matric Number</label>
                                <input type="text" name="matricNumber" class="form-control"
                                       value="${matricNumber}" placeholder="e.g. CS/ND2/001" required>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Academic Level</label>
                                    <select name="academicLevel" class="form-select" required>
                                        <option value="" disabled ${empty academicLevel ? 'selected' : ''}>Select level</option>
                                        <option value="ND1" ${academicLevel == 'ND1' ? 'selected' : ''}>ND1</option>
                                        <option value="ND2" ${academicLevel == 'ND2' ? 'selected' : ''}>ND2</option>
                                        <option value="HND1" ${academicLevel == 'HND1' ? 'selected' : ''}>HND1</option>
                                        <option value="HND2" ${academicLevel == 'HND2' ? 'selected' : ''}>HND2</option>
                                    </select>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Class Group (optional)</label>
                                    <input type="text" name="classGroup" class="form-control"
                                           value="${classGroup}" placeholder="e.g. ND2A">
                                </div>
                            </div>

                            <button type="submit" class="btn btn-department-primary w-100 py-2 mt-2">
                                <i class="bi bi-person-check me-1"></i>Create Account
                            </button>
                        </form>

                        <p class="text-center mt-4 mb-0">
                            Already have an account?
                            <a href="${pageContext.request.contextPath}/login.jsp">Login here</a>
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