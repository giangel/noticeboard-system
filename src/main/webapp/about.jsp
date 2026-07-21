<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>About - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <section class="hero-section py-4">
        <div class="container text-center">
            <h1 class="h2 mb-0">About This Platform</h1>
        </div>
    </section>

    <section class="container my-5">
        <div class="row">
            <div class="col-lg-8 mx-auto">
                <h2 class="section-heading">Why This System Exists</h2>
                <p>
                    Communication is central to the smooth running of academic institutions. Within
                    the Department of Computer Science, important academic and administrative
                    information, such as examination timetables, lecture changes, seminar
                    announcements, result releases, and departmental directives, has traditionally
                    been communicated through a physical notice board located within the departmental
                    premises.
                </p>
                <p>
                    Students who are off campus, absent, ill, or simply do not pass by the notice
                    board on a particular day may miss important time sensitive information. Notices
                    may also be torn, defaced, damaged, or removed, with no reliable record remaining
                    of what was previously published.
                </p>

                <h2 class="section-heading mt-5">What This Platform Provides</h2>
                <ul>
                    <li>A centralized and remotely accessible platform for official departmental notices.</li>
                    <li>Search and filtering by category, urgency, academic level, and class.</li>
                    <li>Persistent digital records of published notices.</li>
                    <li>Targeted communication to specific academic levels or classes.</li>
                    <li>A bookmark feature so students can save important notices for later.</li>
                </ul>

                <h2 class="section-heading mt-5">Case Study</h2>
                <p>
                    This system was designed and implemented for the Department of Computer Science,
                    Adeseun Ogundoyin Polytechnic, Eruwa, as an academic project demonstrating the
                    application of web technology to departmental communication.
                </p>
            </div>
        </div>
    </section>

    <jsp:include page="/includes/footer.jsp" />
    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>