<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="/WEB-INF/head.jsp"/>
<body>
<form method="POST" action="${pageContext.request.contextPath}/CreateAccountServlet">
    <div class="container d-flex flex-column min-vh-100 justify-content-center align-items-center">
        <div class="mb-4 col-xs-4">
            <label for="email" class="form-label">Email</label>
            <input type="text" class="form-control" name="email" id="email">
        </div>
        <div class="mb-4 col-xs-4">
            <label for="fName" class="form-label">First Name</label>
            <input type="text" class="form-control" name="fName" id="fName">
        </div>
        <div class="mb-4 col-xs-4">
            <label for="lName" class="form-label">Last name</label>
            <input type="text" class="form-control" name="lName" id="lName">
        </div>
        <input class="form-control btn btn-outline-primary" type="submit" id="create" value="Create Account"/>
        <a href="index.jsp" class="btn btn-outline-secondary" style="margin-top: 15px;">
            Back to Homepage
        </a>
    </div>
</form>
<hr>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>