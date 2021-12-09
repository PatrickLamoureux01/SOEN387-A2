<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
    <h5>Viewing the results for poll # ${pollId}: ${pollName}</h5>
    <h5>Question: ${pollQuestion}</h5>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">Choice</th>
            <th scope="col"># Votes</th>
        </tr>
        </thead>
        <c:forEach var="res" items="${results}">
            <tr>
                <td>${res.key}</td>
                <td>${res.value}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="manager_index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
        Back to Homepage
    </a>
</div>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>
