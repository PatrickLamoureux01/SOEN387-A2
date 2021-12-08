<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/VoteAnonServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <div class="card">
            <div class="card-header">Poll ID: ${pollId} Poll Name: ${pollName}</div>
            <div class="card-body">
                <h5 class="card-title">Question: ${pollQ}</h5>
            <c:forEach var="choice" items="${choices}">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="pollChoice" id="pollChoice"
                           value="${choice.id}">
                    <label class="form-check-label" for="pollChoice">
                        <c:choose>
                            <c:when test="${empty choice.description}">
                                <c:out value="${choice.text}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${choice.text}"/> - <c:out value="${choice.description}"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>
            </c:forEach>
            </div>
        </div>
        <button type="submit" name="vote" class="btn btn-primary" style="margin-top:15px;">VOTE</button>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>