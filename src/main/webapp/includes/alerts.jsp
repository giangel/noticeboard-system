<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show alert-auto-dismiss" role="alert">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>${errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show alert-auto-dismiss" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i>${successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>