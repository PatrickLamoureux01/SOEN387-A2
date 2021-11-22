<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <h5>Dispalying polls created by: ${fName} ${lName}</h5>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Poll ID</th>
                <th scope="col">Poll Name</th>
                <th scope="col">Poll Question</th>
                <th scope="col">Poll Status</th>
            </tr>
            </thead>
        <c:forEach var="poll" items="${getPolls}">
            <tr>
                <td>${poll.id}</td>
                <td>${poll.name}</td>
                <td>${poll.question}</td>
                <td>${poll.status}</td>
            </tr>
        </c:forEach>
        </table>
    </div>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>