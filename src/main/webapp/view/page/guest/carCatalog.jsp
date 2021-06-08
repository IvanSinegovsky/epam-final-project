<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/view/fragment/navbar.jspf"%>
    <%@ include file="/view/fragment/bootstrapImport.jspf"%>
    <title>Car catalog plug</title>
</head>
<body>
    <h1>CAR CATALOG</h1>
    <div>
    <jsp:useBean id="allCars" scope="request" type="java.util.List"/>

    <c:forEach items="${allCars}" var="car">
        <div>${car}</div>
    </c:forEach>
    </div>

</body>
</html>
