<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer statistics</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>
<body>
<jsp:useBean id="customer_statistics" scope="request" type="by.epam.carrentalapp.bean.dto.CustomerStatisticsDto"/>
<a class="btn btn-primary"
   href="home?command=ORDER_REQUEST_LIST"
   role="button">
    TOCHANGE Back to list
</a>
<h1>
    TOCHANGE customer statistics
</h1>
<div>
    <h5>TOCHANGE rides: <c:out value="${customer_statistics.ridesQuantity}"/></h5>
    <h5>TOCHANGE car accidents: <c:out value="${customer_statistics.carAccidentQuantity}"/></h5>
    <h5>TOCHANGE undid order requests: <c:out value="${customer_statistics.undidOrderRequestQuantity}"/></h5>
    <h5>TOCHANGE rate: <c:out value="${customer_statistics.rate}"/></h5>
    <h5>TOCHANGE money spent: <c:out value="${customer_statistics.moneySpent}"/></h5>
</div>
</body>
</html>