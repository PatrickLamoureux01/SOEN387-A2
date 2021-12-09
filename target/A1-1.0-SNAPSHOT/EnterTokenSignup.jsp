<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/ValidateTokenServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <h5>An e-mail has been sent to your e-mail address containing a verification token.</h5>
        <h5>Please enter the verification token to confirm your sign-up.</h5>
        <div class="mb-4">
            <label for="token" class="form-label">Token</label>
            <input type="text" class="form-control" name="token" id="token">
        </div>
        <button type="submit" name="changeVote" class="btn btn-primary">Verify Token</button>
        <% if (session.getAttribute("invalid_token") == "true") { %>
        <p class="text-danger">The token you entered is invalid.</p>
        <%} %>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>