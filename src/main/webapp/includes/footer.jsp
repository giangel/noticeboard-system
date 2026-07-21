<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<footer class="footer-department">
    <div class="container">
        <div class="row g-4">
            <div class="col-md-4">
                <h5><i class="bi bi-mortarboard-fill me-2"></i>CS Notice Board</h5>
                <p class="small">
                    The official online notice board of the Department of Computer Science,
                    Adeseun Ogundoyin Polytechnic, Eruwa. Built to keep students informed
                    anytime, anywhere.
                </p>
            </div>
            <div class="col-md-4">
                <h5>Quick Links</h5>
                <ul class="list-unstyled small">
                    <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/about.jsp">About the Department</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact.jsp">Contact Us</a></li>
                    <li><a href="${pageContext.request.contextPath}/login.jsp">Student Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register.jsp">Student Registration</a></li>
                </ul>
            </div>
            <div class="col-md-4">
                <h5>Department Address</h5>
                <p class="small">
                    Department of Computer Science<br>
                    Adeseun Ogundoyin Polytechnic<br>
                    Eruwa, Oyo State, Nigeria
                </p>
            </div>
        </div>
        <hr class="border-secondary mt-3">
        <p class="text-center small mb-0">
            Copyright <%= java.time.Year.now() %> Department of Computer Science,
            Adeseun Ogundoyin Polytechnic, Eruwa. All rights reserved.
        </p>
    </div>
</footer>