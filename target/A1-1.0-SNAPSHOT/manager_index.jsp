<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.a1.Poll" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>

<form>
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <div class="card">
            <h5 class="card-header">Welcome, ${fName} ${lName}!</h5>
            <div class="card-body">
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <h5 class="card-title">Current Poll: N/A</h5>
                        <h5 class="card-title">Poll Status: N/A</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="card-title">Current Poll: ${poll.name}</h5>
                        <h5 class="card-title">Poll Status: ${poll.status}</h5>
                    </c:otherwise>
                </c:choose>
                <p class="card-text">Please select from the following list of actions:</p>
                <a href="PollCreation.jsp" class="btn btn-outline-primary">
                    Create a
                    Poll
                </a>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=run" class="btn disabled btn-outline-primary">Run
                            Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=run" class="btn btn-outline-primary">Run
                            Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=update" class="btn disabled btn-outline-primary">
                            Update
                            Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=update" class="btn btn-outline-primary">
                            Update
                            Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=release" class="btn disabled btn-outline-primary">
                            Release
                            Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=release" class="btn btn-outline-primary">
                            Release
                            Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=unrelease" class="btn disabled btn-outline-primary">
                            Unrelease
                            Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=unrelease" class="btn btn-outline-primary">
                            Unrelease
                            Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=clear" class="btn disabled btn-outline-primary">
                            Clear Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=clear" class="btn btn-outline-primary">
                            Clear Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.name==null}">
                        <a href="PollServlet?type=close" class="btn disabled btn-outline-primary">
                            Close
                            Poll
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=close" class="btn btn-outline-primary">
                            Close
                            Poll
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.status=='RELEASED'}">
                        <a href="view_results.jsp" class="btn btn-outline-primary">
                            View Poll Results
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="view_results.jsp" class="btn disabled btn-outline-primary">
                            View Poll Results
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${poll.status=='RELEASED'}">
                        <a href="PollServlet?type=download" class="btn btn-outline-primary">
                            Download Poll Results
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="PollServlet?type=download" class="btn disabled btn-outline-primary">
                            Download Poll Results
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <a href="index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
            Back to Homepage
        </a>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>