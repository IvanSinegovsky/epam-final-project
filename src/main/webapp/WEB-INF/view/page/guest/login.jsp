<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

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
<div style="max-width: 250px;text-align:center;margin-left:40%;margin-top: 7%">
        <h4 class="card-title mt-3 text-center" style="margin-bottom: 20px">
            <c:out value="${loginSign}"/>
        </h4>
        <form method="POST" action="http://localhost:8080/home?command=LOGIN">
            <div class="form-group input-group">
                <input name="email"
                       class="form-control"
                       placeholder="<c:out value="${emailInput}"/>"
                       type="email">
            </div>
            <div class="form-group input-group">
                <input name="password"
                       class="form-control"
                       placeholder="<c:out value="${passwordInput}"/>"
                       type="password">
            </div>
            <div class="form-group" style="margin-top: 30px">
                <button type="submit" class="btn btn-primary"><c:out value="${loginButton}"/></button>
            </div>
            <p class="text-center"><c:out value="${notRegisteredYetSign}"/>
                <a href="http://localhost:8080/home?command=HOME" style="margin-left: 5px">
                    <c:out value="${registerReferenceSign}"/>
                </a>
            </p>
        </form>
</div>
</body>
</html>