<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thanks for order request</title>

    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="customer.saved-order-request-info.thanks-for-your-request-sign" var="thanksSign"/>
    <fmt:message bundle="${loc}" key="customer.saved-order-request-info.order-info-sign" var="orderInfoSign"/>
    <fmt:message bundle="${loc}" key="customer.saved-order-request-info.email-notification-sign" var="emailNotificationSign"/>
</head>
<body>
<jsp:useBean id="order_request_info" scope="request" type="by.epam.carrentalapp.bean.entity.OrderRequest"/>
<jsp:useBean id="expected_car" scope="request" type="by.epam.carrentalapp.bean.entity.Car"/>

<div class="row">
    <div class="col" style="margin-left: 5%">
        <img class="card-img-top" src="${expected_car.assetURL}" alt="Card image cap">
    </div>
    <div class="col">
        <h1><c:out value="${thanksSign}"/></h1>
        <br/>
        <h3><c:out value="${orderInfoSign}"/></h3>
        <div style="margin-bottom: 20px;margin-top: 20px">
            <h5><c:out value="${expected_car.model}"/></h5>
            <h5><c:out value="${order_request_info.expectedStartTime}"/></h5>
            <h5><c:out value="${order_request_info.expectedEndTime}"/></h5>
        </div>
        <h5><i><c:out value="${emailNotificationSign}"/></i></h5>

    </div>
</div>
</body>
</html>