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
<div class="card bg-light">
    
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center">
            <c:out value="${createAccountSign}"/>
        </h4>

        <form method="POST" action="home">
            <input type="hidden" name="command" value="REGISTER"/>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="name"
                       class="form-control"
                       placeholder="<c:out value="${nameInput}"/>"
                       type="text">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>

                <input name="lastname"
                       class="form-control"
                       placeholder="<c:out value="${lastNameInput}"/>"
                       type="text">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email"
                       class="form-control"
                       placeholder="<c:out value="${emailAddressInput}"/>"
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

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="passport_number"
                       class="form-control"
                       placeholder="<c:out value="${passportNumberInput}"/>"
                       type="text">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary"><c:out value="${registerButton}"/></button>
            </div>
            <p class="text-center"><c:out value="${haveAnAccountSign}"/><a href="home?command=LOGIN"><c:out value="${logInReferenceSign}"/></a></p>
        </form>
    </article>
</div>
</body>
</html>
