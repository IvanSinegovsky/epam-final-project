<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <c:out value="${sessionScope.local}"/>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources" var="loc"/>
    <fmt:message bundle="${loc}" key="guest.register.create-account" var="message"/>

    <c:out value="${message}"/>


    <form method="POST" action="home">
        <input type="hidden" name="command" value="ru"/>
        <input type="submit" value="ru">
    </form>
    <br/>
    <form method="POST" action="home">
        <input type="hidden" name="command" value="en"/>
        <input type="submit" value="en">
    </form>
</form>
</body>
</html>
