<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
    <h1>Thank you for submitting your vote!</h1>
    <h1>Your PIN # to use to access this vote in the future is: ${pin}</h1>
    <a href="manager_index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
        Back to Homepage
    </a>
</div>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>