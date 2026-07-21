<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.noticeboard.dao.NoticeDAO" %>
<%@ page import="com.noticeboard.dao.NoticeCategoryDAO" %>
<%@ page import="com.noticeboard.dao.UserDAO" %>
<%@ page import="com.noticeboard.model.Notice" %>
<%@ page import="com.noticeboard.model.NoticeCategory" %>
<%
    // Single data retrieval block for the homepage, no HTML is generated here
    NoticeDAO homeNoticeDAO = new NoticeDAO();
    NoticeCategoryDAO homeCategoryDAO = new NoticeCategoryDAO();
    UserDAO homeUserDAO = new UserDAO();

    List<Notice> latestNotices = homeNoticeDAO.findLatestPublished(6);
    List<Notice> urgentNotices = homeNoticeDAO.findByUrgency("URGENT", 3);
    List<NoticeCategory> allCategories = homeCategoryDAO.findAll();

    int totalPublished = homeNoticeDAO.countByStatus("PUBLISHED");
    int totalStudents = homeUserDAO.countAllStudents();
    int totalCategories = allCategories.size();

    request.setAttribute("latestNotices", latestNotices);
    request.setAttribute("urgentNotices", urgentNotices);
    request.setAttribute("allCategories", allCategories);
    request.setAttribute("totalPublished", totalPublished);
    request.setAttribute("totalStudents", totalStudents);
    request.setAttribute("totalCategories", totalCategories);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CS Department Notice Board - Adeseun Ogundoyin Polytechnic, Eruwa</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-public.jsp" />

    <!-- HERO / WELCOME SECTION -->
    <section class="hero-section">
        <div class="container text-center">
            <h1 class="display-5 mb-3">Official Online Notice Board</h1>
            <p class="lead mb-4">
                Department of Computer Science, Adeseun Ogundoyin Polytechnic, Eruwa. Stay informed
                on examinations, lectures, seminars, results, and departmental directives, from anywhere,
                anytime.
            </p>

            <form action="${pageContext.request.contextPath}/search-notices" method="get" class="mx-auto"
                  style="max-width: 640px;">
                <div class="hero-search-box d-flex">
                    <input type="text" name="keyword" class="form-control border-0"
                           placeholder="Search notices by title or content...">
                    <button type="submit" class="btn btn-department-primary px-4">
                        <i class="bi bi-search"></i>
                    </button>
                </div>
            </form>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-light btn-lg me-2">
                    <i class="bi bi-box-arrow-in-right me-1"></i>Student Login
                </a>
                <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-outline-light btn-lg">
                    <i class="bi bi-person-plus me-1"></i>Register
                </a>
            </div>
        </div>
    </section>

    <!-- STATISTICS SECTION -->
    <section class="container my-5">
        <div class="row g-3 text-center">
            <div class="col-md-4">
                <div class="stat-card stat-card-primary">
                    <div class="stat-number">${totalPublished}</div>
                    <div class="stat-label">Published Notices</div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card stat-card-success">
                    <div class="stat-number">${totalStudents}</div>
                    <div class="stat-label">Registered Students</div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card stat-card-dark">
                    <div class="stat-number">${totalCategories}</div>
                    <div class="stat-label">Notice Categories</div>
                </div>
            </div>
        </div>
    </section>

    <!-- URGENT NOTICES SECTION -->
    <c:if test="${not empty urgentNotices}">
        <section class="container mb-5">
            <h2 class="section-heading">Urgent Notices</h2>
            <div class="row g-3">
                <c:forEach var="notice" items="${urgentNotices}">
                    <div class="col-md-4">
                        <div class="card notice-card">
                            <div class="card-body">
                                <span class="badge badge-urgency-urgent mb-2">Urgent</span>
                                <span class="badge badge-category mb-2">${notice.categoryName}</span>
                                <h5 class="card-title">${notice.title}</h5>
                                <p class="notice-preview">
                                    <c:out value="${notice.content}" />
                                </p>
                                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-sm btn-department-primary">
                                    View Details
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </c:if>

    <!-- LATEST NOTICES SECTION -->
    <section class="container mb-5">
        <h2 class="section-heading">Latest Notices</h2>
        <c:choose>
            <c:when test="${empty latestNotices}">
                <div class="empty-state">
                    <i class="bi bi-inbox"></i>
                    <p>No notices have been published yet. Please check back soon.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row g-3">
                    <c:forEach var="notice" items="${latestNotices}">
                        <div class="col-md-4">
                            <div class="card notice-card">
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${notice.urgencyLevel == 'URGENT'}">
                                            <span class="badge badge-urgency-urgent mb-2">Urgent</span>
                                        </c:when>
                                        <c:when test="${notice.urgencyLevel == 'IMPORTANT'}">
                                            <span class="badge badge-urgency-important mb-2">Important</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-urgency-normal mb-2">Normal</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="badge badge-category mb-2">${notice.categoryName}</span>
                                    <h5 class="card-title">${notice.title}</h5>
                                    <p class="notice-preview">
                                        <c:out value="${notice.content}" />
                                    </p>
                                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-sm btn-department-primary">
                                        View Details
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <!-- CATEGORIES SECTION -->
    <section class="container mb-5">
        <h2 class="section-heading">Notice Categories</h2>
        <div class="d-flex flex-wrap gap-2">
            <c:forEach var="category" items="${allCategories}">
                <span class="badge badge-category px-3 py-2">${category.categoryName}</span>
            </c:forEach>
        </div>
    </section>

    <!-- CALL TO ACTION -->
    <section class="container mb-5">
        <div class="p-5 text-center rounded" style="background-color: #e8edf5;">
            <h3 class="mb-3">Never Miss a Departmental Notice Again</h3>
            <p class="mb-4">
                Register today to receive full access to search, filtering, bookmarks, and your
                personalized notice dashboard.
            </p>
            <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-department-primary btn-lg">
                Create Your Student Account
            </a>
        </div>
    </section>

    <jsp:include page="/includes/footer.jsp" />
    <jsp:include page="/includes/scripts.jsp" />
</body>
</html>