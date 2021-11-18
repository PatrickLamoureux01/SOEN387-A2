<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp" />
<body>
<form method="POST" action="${pageContext.request.contextPath}/ManagerServlet">
<div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
    <p>If you are a poll manager, please enter your passcode below:</p>
    <input class="form-control btn-block" type="password" name="passcode"/>
    <input class="form-control btn btn-outline-primary" type="submit" id="proceed" value="PROCEED"/>
    <p>Otherwise, if you are a participant, please click <a href="user_index.jsp">here</a> to proceed.</p>
    <% if (session.getAttribute("invalid") == "true") { %>
    <p class="text-danger">The passcode is invalid.</p>
    <%} %>
</div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp" />
</body>
</html>