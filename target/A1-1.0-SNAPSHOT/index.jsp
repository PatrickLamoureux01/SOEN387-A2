<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/UserLoginServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <p>Please enter your username and password below:</p>
        <div class="mb-4 col-xs-4">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" name="email" id="email">
        </div>
        <div class="mb-4 col-xs-4">
            <label for="passcode" class="form-label">Password</label>
            <input type="password" class="form-control" name="passcode" id="passcode">
        </div>
        <input class="form-control btn btn-outline-primary" type="submit" id="proceed" value="PROCEED"/>
        <p>If you wish to remain anonymous, please click <a href="PollServlet?type=vote_anon">here</a> to vote.</p>
        <p>If you do not have an account yet, please click <a href="create_account.jsp">here</a> to create one.</p>
        <p>If you have forgotten your password, please click <a href="forgot_password.jsp">here</a> to recover it.</p>
        <a class="btn btn-primary" href="RunTestsServlet">Run Tests</a>
        <% if (session.getAttribute("invalid") == "true") { %>
        <p class="text-danger">The email/passcode is invalid.</p>
        <%} %>
        <% if (session.getAttribute("validated_token") == "true") { %>
        <p class="text-success">Your account has been verified!</p>
        <%} %>
        <% if (session.getAttribute("forgot_token") == "true") { %>
        <p class="text-success">Your password has been updated!</p>
        <%} %>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>