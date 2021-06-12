<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
    <title>Order request list</title>
</head>
<body>
<jsp:useBean id="orderRequestInfos" scope="request" type="java.util.List"/>

<c:forEach items="${orderRequestInfos}" var="orderRequestInfo">
    <div class="card-group">
        <div class="card">
            <div class="card-body">
                <p class="card-text"><small class="text-muted">Order request id: ${orderRequestInfo.orderRequestId}</small></p>
                <h5 class="card-title">Customer rate: ${orderRequestInfo.customerRate}</h5>
                <h5 class="card-title">${orderRequestInfo.expectedCarModel}</h5>
                <h5 class="card-title">${orderRequestInfo.expectedStartTime}</h5>
                <h5 class="card-title">${orderRequestInfo.expectedEndTime}</h5>
            </div>
            <button type="button" class="btn btn-success">Accept</button>
            <button type="button" class="btn btn-danger">Reject</button>
        </div>
</c:forEach>
</body>
</html>