<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
    <title>Car catalog</title>
</head>
<body>
        <jsp:useBean id="all_cars" scope="request" type="java.util.List"/>

        <c:forEach items="${all_cars}" var="car">
        <div class="card-group">
            <div class="card">
            <img class="card-img-top" src="https://westgroup.by/d/porsh-gobrid-logo-west.png" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">${car.model}</h5>
                <p class="card-text">${car.number}</p>
                <p class="card-text"><small class="text-muted">${car.hourlyCost}</small></p>
            </div>
        </div>
        </div>
        </c:forEach>
</body>
</html>
