<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer statistics</title>

    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.back-to-order-request-list-button" var="backToOrderRequestListButton"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.customer-statistics-sign" var="customerStatisticsSign"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.rides-quantity" var="ridesQuantity"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.car-accidents-quantity" var="carAccidentsQuantity"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.undid-order-requests-quantity" var="undidOrderRequestsQuantity"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.total-rate" var="totalRate"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.customer-statistics.total-money-spent" var="totalMoneySpent"/>
</head>

<body>
<jsp:useBean id="customer_statistics" scope="request" type="by.epam.carrentalapp.bean.dto.CustomerStatisticsDto"/>
<a class="btn btn-primary"
   href="home?command=ORDER_REQUEST_LIST"
   role="button"><c:out value="${backToOrderRequestListButton}"/></a>
<h1><c:out value="${customerStatisticsSign}"/></h1>
<div>
    <h5><c:out value="${ridesQuantity}"/><c:out value="${customer_statistics.ridesQuantity}"/></h5>
    <h5><c:out value="${carAccidentsQuantity}"/><c:out value="${customer_statistics.carAccidentQuantity}"/></h5>
    <h5><c:out value="${undidOrderRequestsQuantity}"/><c:out value="${customer_statistics.undidOrderRequestQuantity}"/></h5>
    <h5><c:out value="${totalRate}"/><c:out value="${customer_statistics.rate}"/></h5>
    <h5><c:out value="${totalMoneySpent}"/><c:out value="${customer_statistics.moneySpent}"/></h5>
</div>
</body>
</html>