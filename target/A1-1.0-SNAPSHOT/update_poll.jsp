<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/UpdateServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <div class="mb-4">
            <label for="pollName" class="form-label">Poll Name</label>
            <input type="text" class="form-control" name="pollName" id="pollName" aria-describedby="pollHelp" value="${param.name}">
            <div id="pollHelp" class="form-text">Enter the updated name for your poll.</div>
        </div>
        <div class="mb-4">
            <label for="pollQuestion" class="form-label">Question</label>
            <input type="text" class="form-control" name="pollQuestion" id="pollQuestion" aria-describedby="questionHelp" value="${param.q}">
            <div id="questionHelp" class="form-text">Enter the updated question for your poll.</div>
        </div>
        <div class="mb-4">
            <label for="pollChoices" class="form-label">Choices</label>
            <input type="text" class="form-control" name="pollChoices" id="pollChoices" aria-describedby="choicesHelp">
            <div id="choicesHelp" class="form-text">Enter the updated choices for your poll in the following format: Choice:(optional description),... </div>
        </div>
        <input hidden type="text" class="form-control" name="pollId" id="pollId" aria-describedby="pollHelp" value="${param.id}">
        <button type="submit" name="pollUpdate" class="btn btn-primary">Update Poll</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>