<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create Notice - CS Department Notice Board</title>
    <jsp:include page="/includes/head.jsp" />
</head>
<body data-context-path="${pageContext.request.contextPath}">

    <jsp:include page="/includes/navbar-dashboard.jsp" />

    <c:set var="activePage" value="createNotice" scope="request" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="/includes/sidebar-admin.jsp" />

            <div class="col-lg-10 dashboard-content">
                <h2 class="section-heading">Create New Notice</h2>


                <div class="card notice-card">
                    <div class="card-body p-4">
                        <form action="${pageContext.request.contextPath}/admin/create-notice" method="post">
                            <div class="mb-3">
                                <label class="form-label">Notice Title</label>
                                <input type="text" name="title" class="form-control" value="${title}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Notice Content</label>
                                <textarea name="content" class="form-control" rows="6" required>${content}</textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Category</label>
                                    <select name="categoryId" class="form-select" required>
                                        <option value="" disabled selected>Select category</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryId}">${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4 mb-3">
                                    <label class="form-label">Urgency Level</label>
                                    <select name="urgencyLevel" class="form-select">
                                        <option value="NORMAL" selected>Normal</option>
                                        <option value="IMPORTANT">Important</option>
                                        <option value="URGENT">Urgent</option>
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
                                    <option value="ALL" selected>All Students</option>
                                    <option value="LEVEL">Specific Academic Level</option>
                                    <option value="CLASS">Specific Class Group</option>
                                </select>
                            </div>

                            <div class="row" id="levelFieldWrapper" style="display: none;">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Academic Level</label>
                                    <select name="academicLevel" class="form-select">
                                        <option value="ND1">ND1</option>
                                        <option value="ND2">ND2</option>
                                        <option value="HND1">HND1</option>
                                        <option value="HND2">HND2</option>
                                    </select>
                                </div>
                                <div class="col-md-6 mb-3" id="classFieldWrapper" style="display: none;">
                                    <label class="form-label">Class Group</label>
                                    <input type="text" name="classGroup" class="form-control" placeholder="e.g. ND2A">
                                </div>
                            </div>

                            <div class="d-flex gap-2 mt-3">
                                <button type="submit" name="action" value="draft" class="btn btn-outline-secondary">
                                    <i class="bi bi-save me-1"></i>Save as Draft
                                </button>
                                <button type="submit" name="action" value="publish" class="btn btn-department-primary">
                                    <i class="bi bi-send-check me-1"></i>Publish Now
                                </button>
                                <a href="${pageContext.request.contextPath}/admin/notices" class="btn btn-outline-danger ms-auto">
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
    </script>
</body>
</html>