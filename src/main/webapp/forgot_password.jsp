<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/ForgotPasswordServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <p>Please enter your email address below to be sent a recovery token.</p>
        <div class="mb-4 col-xs-4">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" name="email" id="email">
        </div>
        <input class="form-control btn btn-outline-primary" type="submit" id="recover" value="Recover Password"/>
        <a href="index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
            Back to Homepage
        </a>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>