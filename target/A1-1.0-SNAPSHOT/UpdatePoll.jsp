<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
    <p>Select the poll you would like to update.</p>
    <table class="table table-hover">
        <thead>
            <tr>
                <th scope="col">Poll ID</th>
                <th scope="col">Poll Name</th>
                <th scope="col">Poll Question</th>
                <th></th>
            </tr>
        </thead>
        <c:forEach var="poll" items="${updatePolls}">
            <tr>
                <th scope="row">${poll.id}</th>
                <td>${poll.name}</td>
                <td>${poll.question}</td>
                <td><a href="update_poll.jsp?id=${poll.id}&name=${poll.name}&q=${poll.question}" class="btn btn-dark">Update</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>