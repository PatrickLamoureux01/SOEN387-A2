<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/ChangeVoteServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <h5>Please enter the PIN that you were given at the time of your voting.</h5>
        <div class="mb-4">
            <label for="pin" class="form-label">PIN #</label>
            <input type="text" class="form-control" name="pin" id="pin" aria-describedby="pollHelp">
        </div>
        <button type="submit" name="changeVote" class="btn btn-primary">Access Poll</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>