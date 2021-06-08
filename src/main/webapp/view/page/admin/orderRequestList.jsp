<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/view/fragment/navbar.jspf"%>
    <%@ include file="/view/fragment/bootstrapImport.jspf"%>
    <title>Order request list</title>
</head>
<body>
<jsp:useBean id="orderRequests" scope="request" type="java.util.List"/>

<c:forEach items="${orderRequests}" var="orderRequest">
    ${orderRequest}
    <%--
    <div class="card-group">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">${car.model}</h5>
                <p class="card-text">${car.number}</p>
                <p class="card-text"><small class="text-muted">${car.hourlyCost}</small></p>
            </div>
        </div>
    </div>--%>
</c:forEach>
</body>
</html>