<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/ChangePasswordServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <p>Please enter your current password below.</p>
        <div class="mb-4 col-xs-4">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" name="password" id="password">
        </div>
        <input class="form-control btn btn-outline-primary" type="submit" id="change" value="Change Password"/>
        <% if (session.getAttribute("invalid_change") == "true") { %>
        <p class="text-danger">That password is invalid.</p>
        <%} %>
        <a href="index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
            Back to Homepage
        </a>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>