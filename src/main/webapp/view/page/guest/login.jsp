<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/view/fragment/navbar.jspf"%>
    <title>Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources" var="loc"/>
    <fmt:message bundle="${loc}" key="guest.login.login-sign" var="loginSign"/>
    <fmt:message bundle="${loc}" key="guest.login.email-input" var="emailInput"/>
    <fmt:message bundle="${loc}" key="guest.login.password-input" var="passwordInput"/>
    <fmt:message bundle="${loc}" key="guest.login.login-button" var="loginButton"/>
    <fmt:message bundle="${loc}" key="guest.login.not-registered-yet" var="notRegisteredYetSign"/>
    <fmt:message bundle="${loc}" key="guest.login.register-reference" var="registerReferenceSign"/>
</head>

<body>
<div class="card bg-light">

    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center">
            <c:out value="${loginSign}"/>
        </h4>

        <form method="POST" action="http://localhost:8080/home?command=LOGIN">

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email"
                       class="form-control"
                       placeholder="<c:out value="${emailInput}"/>"
                       type="email">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>

                <input name="password"
                       class="form-control"
                       placeholder="<c:out value="${passwordInput}"/>"
                       type="password">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary"><c:out value="${loginButton}"/></button>
            </div>
            <p class="text-center"><c:out value="${notRegisteredYetSign}"/><a href="http://localhost:8080/home?command=HOME"><c:out value="${registerReferenceSign}"/></a></p>
        </form>
    </article>
</div>
</body>
</html>