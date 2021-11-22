<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/PollReleaseServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <p>Select the poll you would like to release.</p>
        <h5>Please note that only polls that are running will appear in this list.</h5>
        <c:forEach var="poll" items="${releasePolls}">
            <input type="radio" id="${poll.id}" name="poll_to_release" value="${poll.id}">
            <label for="${poll.id}">${poll.name} - ${poll.question}</label><br>
        </c:forEach>
        <button type="submit" name="pollRelease" class="btn btn-primary">Release Poll</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>