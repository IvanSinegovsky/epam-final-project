<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="guest.register.create-account-sign" var="createAccountSign"/>
    <fmt:message bundle="${loc}" key="guest.register.name-input" var="nameInput"/>
    <fmt:message bundle="${loc}" key="guest.register.lastname-input" var="lastNameInput"/>
    <fmt:message bundle="${loc}" key="guest.register.email-input" var="emailAddressInput"/>
    <fmt:message bundle="${loc}" key="guest.register.password-input" var="passwordInput"/>
    <fmt:message bundle="${loc}" key="guest.register.passport-number-input" var="passportNumberInput"/>
    <fmt:message bundle="${loc}" key="guest.register.register-button" var="registerButton"/>
    <fmt:message bundle="${loc}" key="guest.register.have-an-account" var="haveAnAccountSign"/>
    <fmt:message bundle="${loc}" key="guest.register.log-in-reference" var="logInReferenceSign"/>
</head>

<body>
<div style="max-width: 250px;text-align:center;margin-left:40%">
        <h4 class="card-title mt-3 text-center" style="margin-top: 40px; margin-bottom: 20px">
            <c:out value="${createAccountSign}"/>
        </h4>

        <form method="POST" action="home">
            <input type="hidden" name="command" value="REGISTER"/>

            <div class="form-group input-group">
                <input name="name"
                       class="form-control"
                       placeholder="<c:out value="${nameInput}"/>"
                       type="text">
            </div>

            <div class="form-group input-group">
                <input name="lastname"
                       class="form-control"
                       placeholder="<c:out value="${lastNameInput}"/>"
                       type="text">
            </div>

            <div class="form-group input-group">
                <input name="email"
                       class="form-control"
                       placeholder="<c:out value="${emailAddressInput}"/>"
                       type="email">
            </div>

            <div class="form-group input-group">
                <input name="password"
                       class="form-control"
                       placeholder="<c:out value="${passwordInput}"/>"
                       type="password">
            </div>

            <div class="form-group input-group">
                <input name="passport_number"
                       class="form-control"
                       placeholder="<c:out value="${passportNumberInput}"/>"
                       type="text">
            </div>

            <div class="form-group" style="margin-top: 30px">
                <button type="submit" class="btn btn-primary"><c:out value="${registerButton}"/></button>
            </div>
            <p class="text-center" style="margin-left: 5px">
                <c:out value="${haveAnAccountSign}"/>
                <a href="home?command=LOGIN">
                    <c:out value="${logInReferenceSign}"/>
                </a>
            </p>
        </form>
</div>
</body>
</html>
