<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Contact Us - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="hero-section py-4">
        <div class="container text-center">
            <h1 class="h2 mb-0">Contact the Department</h1>
        </div>
    </section>

    <section class="container my-5">
        <div class="row g-4">
            <div class="col-md-4">
                <div class="card notice-card h-100">
                    <div class="card-body text-center">
                        <i class="bi bi-geo-alt-fill fs-2 mb-3" style="color: var(--primary-color);"></i>
                        <h5 class="card-title">Office Address</h5>
                        <p class="text-muted mb-0">
                            Department of Computer Science<br>
                            Adeseun Ogundoyin Polytechnic<br>
                            Eruwa, Oyo State, Nigeria
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card notice-card h-100">
                    <div class="card-body text-center">
                        <i class="bi bi-envelope-fill fs-2 mb-3" style="color: var(--primary-color);"></i>
                        <h5 class="card-title">Email</h5>
                        <p class="text-muted mb-0">
                            computerscience@aopoly.edu.ng
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card notice-card h-100">
                    <div class="card-body text-center">
                        <i class="bi bi-clock-fill fs-2 mb-3" style="color: var(--primary-color);"></i>
                        <h5 class="card-title">Office Hours</h5>
                        <p class="text-muted mb-0">
                            Monday to Friday<br>
                            8:00 AM to 4:00 PM
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