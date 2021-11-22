<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/PollDeleteServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <p>Select the poll you would like to delete.</p>
        <h5>Please note that only polls that are created by you</h5>
        <h5>AND</h5>
        <h5>that have not been voted on will appear in this list.</h5>
        <table class="table table-hover">
            <thead>
            <tr>
                <th></th>
                <th scope="col">Poll ID</th>
                <th scope="col">Poll Name</th>
                <th scope="col">Poll Question</th>
            </tr>
            </thead>
        <c:forEach var="poll" items="${pollsToDelete}">
            <tr>
                <th scope="row"><input type="radio" id="${poll.id}" name="poll_to_delete" value="${poll.id}"></th>
                <td>${poll.id}</td>
                <td>${poll.name}</td>
                <td>${poll.question}</td>
            </tr>
        </c:forEach>
        </table>
        <button type="submit" name="pollDelete" class="btn btn-danger">Delete Poll</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>