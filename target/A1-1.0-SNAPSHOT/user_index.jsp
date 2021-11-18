<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.a1.Poll" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/VoteServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <c:choose>
            <c:when test="${poll.status==null or poll.status=='CREATED'}">
                <h1>There is no poll in existence yet.</h1>
            </c:when>
            <c:when test="${poll.status=='RELEASED'}">
                <h1>The poll has been released.</h1>
                <a href="view_results.jsp" class="btn btn-outline-primary" style="margin-bottom:15px;">
                    View Poll Results
                </a>
                <a href="PollServlet?type=download" class="btn btn-outline-primary">
                    Download Poll Results
                </a>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <div class="card-header">Current Poll: ${poll.name}</div>
                    <div class="card-body">
                        <h5 class="card-title">Question: ${poll.question}</h5>
                        <c:forEach var="poll" items="${poll.choices}">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="pollChoice" id="pollChoice"
                                       value="${poll.text}">
                                <label class="form-check-label" for="pollChoice">
                                    <c:choose>
                                        <c:when test="${empty poll.description}">
                                            <c:out value="${poll.text}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${poll.text}"/> - <c:out value="${poll.description}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit Vote</button>
                </div>
            </c:otherwise>
        </c:choose>
        <a href="index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
            Back to Homepage
        </a>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>