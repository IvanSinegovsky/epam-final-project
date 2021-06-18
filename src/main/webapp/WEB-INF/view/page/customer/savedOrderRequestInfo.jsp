<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thanks for order request</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>
<body>
<jsp:useBean id="order_request_info" scope="request" type="by.epam.carrentalapp.bean.entity.OrderRequest"/>
<jsp:useBean id="expected_car" scope="request" type="by.epam.carrentalapp.bean.entity.Car"/>
<h1>
    tochange THANKS FOR ORDER REQUEST
</h1>
<br/>
<h3>
    TOCHANGE Order info:
</h3>
<div>
    <img class="card-img-top" src="${expected_car.assetURL}" alt="Card image cap">
    <h5><c:out value="${expected_car.model}"/></h5>
    <h5><c:out value="${order_request_info.expectedStartTime}"/></h5>
    <h5><c:out value="${order_request_info.expectedEndTime}"/></h5>
</div>
<h5>TOCHANGE you will get your order status in few minutes. Then you should pay</h5>
</body>
</html>