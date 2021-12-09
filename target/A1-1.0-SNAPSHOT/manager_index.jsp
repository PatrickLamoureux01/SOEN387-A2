<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Classes.Poll" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page session="true" %>

<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>

<form>
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <div class="card text-center">
            <div class="card-header">
                <ul class="nav nav-tabs card-header-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Actions</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./change_password.jsp">Change Password</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#">Disabled</a>
                    </li>
                </ul>
            </div>
            <div class="card-body">
                <h5 class="card-title">Welcome, ${fName} ${lName}!</h5>
                <p class="card-text">Please select from the following list of actions:</p>
                <a href="PollServlet?type=vote" class="btn btn-outline-primary">
                    Access Polls
                </a>
                <a href="ChangeVote.jsp" class="btn btn-outline-primary">
                    Change Vote
                </a>
                <a href="PollCreation.jsp" class="btn btn-outline-primary">
                    Create a
                    Poll
                </a>
                <a href="PollServlet?type=delete" class="btn btn-outline-primary">
                    Delete a
                    Poll
                </a>
                <a href="PollServlet?type=run" class="btn btn-outline-primary">Run
                    Poll
                </a>
                <a href="PollServlet?type=update" class="btn btn-outline-primary">
                    Update
                    Poll
                </a>
                <a href="PollServlet?type=release" class="btn btn-outline-primary">
                    Release
                    Poll
                </a>
                <a href="PollServlet?type=unrelease" class="btn btn-outline-primary">
                    Unrelease
                    Poll
                </a>
                <a href="PollServlet?type=clear" class="btn btn-outline-primary">
                    Clear Poll
                </a>
                <a href="PollServlet?type=close" class="btn btn-outline-primary">
                    Close
                    Poll
                </a>
                <a href="PollServlet?type=getpolls" class="btn btn-outline-primary">
                    Get List of polls
                </a>
                <a href="PollServlet?type=viewres" class="btn btn-outline-primary">
                    View Poll Results
                </a>
                <a href="PollServlet?type=download" class="btn btn-outline-primary">
                    Download Poll Results
                </a>
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