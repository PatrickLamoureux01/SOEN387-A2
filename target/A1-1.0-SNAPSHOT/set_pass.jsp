<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/ValidateTokenServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <c:if test="${param.type=='signup'}">
            <h5 class="text-success">Your email address has been verified!</h5>
            <h5>Please choose your password.</h5>
        </c:if>
        <c:if test="${param.type=='forgot'}">
            <h5>Please choose your password.</h5>
        </c:if>
        <c:if test="${param.type=='change'}">
            <h5>Please choose your new password.</h5>
        </c:if>
        <div class="mb-4">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" name="password" id="password">
        </div>
        <button type="submit" name="setPassword" class="btn btn-primary">Set Password</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>