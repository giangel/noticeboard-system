<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Notice - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="notices" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Edit Notice</h2>


                <div class="card notice-card">
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/admin/update-notice" method="post">
                            <input type="hidden" name="noticeId" value="${notice.noticeId}">

                            <div class="mb-3">
                                <label class="form-label">Notice Title</label>
                                <input type="text" name="title" class="form-control" value="${notice.title}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Notice Content</label>
                                <textarea name="content" class="form-control" rows="6" required>${notice.content}</textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Category</label>
                                    <select name="categoryId" class="form-select" required>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryId}"
                                                ${category.categoryId == notice.categoryId ? 'selected' : ''}>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Urgency Level</label>
                                    <select name="urgencyLevel" class="form-select">
                                        <option value="NORMAL" ${notice.urgencyLevel == 'NORMAL' ? 'selected' : ''}>Normal</option>
                                        <option value="IMPORTANT" ${notice.urgencyLevel == 'IMPORTANT' ? 'selected' : ''}>Important</option>
                                        <option value="URGENT" ${notice.urgencyLevel == 'URGENT' ? 'selected' : ''}>Urgent</option>
                                    </select>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Expiry Date (optional)</label>
                                    <input type="date" name="expiryDate" class="form-control">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Target Audience</label>
                                <select name="targetAudience" id="targetAudience" class="form-select" onchange="toggleTargetFields()">
                                    <option value="ALL" ${notice.targetAudience == 'ALL' ? 'selected' : ''}>All Students</option>
                                    <option value="LEVEL" ${notice.targetAudience == 'LEVEL' ? 'selected' : ''}>Specific Academic Level</option>
                                    <option value="CLASS" ${notice.targetAudience == 'CLASS' ? 'selected' : ''}>Specific Class Group</option>
                                </select>
                            </div>

                            <div class="row" id="levelFieldWrapper">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Academic Level</label>
                                    <select name="academicLevel" class="form-select">
                                        <option value="ND1" ${notice.academicLevel == 'ND1' ? 'selected' : ''}>ND1</option>
                                        <option value="ND2" ${notice.academicLevel == 'ND2' ? 'selected' : ''}>ND2</option>
                                        <option value="HND1" ${notice.academicLevel == 'HND1' ? 'selected' : ''}>HND1</option>
                                        <option value="HND2" ${notice.academicLevel == 'HND2' ? 'selected' : ''}>HND2</option>
                                    </select>
                                </div>
                                <div class="col-md-6 mb-3" id="classFieldWrapper">
                                    <label class="form-label">Class Group</label>
                                    <input type="text" name="classGroup" class="form-control" value="${notice.classGroup}">
                                </div>
                            </div>

                            <div class="d-flex gap-2 mt-3">
                                <button type="submit" class="btn btn-department-primary">
                                    <i class="bi bi-save me-1"></i>Save Changes
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/notices" class="btn btn-outline-danger">
                                    Cancel
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/includes/scripts.jsp" />

    <script>
        function toggleTargetFields() {
            var target = document.getElementById('targetAudience').value;
            var levelWrapper = document.getElementById('levelFieldWrapper');
            var classWrapper = document.getElementById('classFieldWrapper');

            if (target === 'ALL') {
                levelWrapper.style.display = 'none';
                classWrapper.style.display = 'none';
            } else if (target === 'LEVEL') {
                levelWrapper.style.display = 'flex';
                classWrapper.style.display = 'none';
            } else if (target === 'CLASS') {
                levelWrapper.style.display = 'flex';
                classWrapper.style.display = 'block';
            }
        }

        document.addEventListener('DOMContentLoaded', toggleTargetFields);
    </script>
</body>
</html>