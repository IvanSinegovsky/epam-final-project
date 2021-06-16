<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Car catalog</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources" var="loc"/>
    <fmt:message bundle="${loc}" key="guest.car-catalog.make-an-order-button" var="makeAnOrderButton"/>
    <fmt:message bundle="${loc}" key="guest.car-catalog.order-popup.order-details-sign" var="orderDetailsSign"/>
    <fmt:message bundle="${loc}" key="guest.car-catalog.order-popup.choose-start-date-and-time-sign" var="chooseStartDateAndTimeSign"/>
    <fmt:message bundle="${loc}" key="guest.car-catalog.order-popup.choose-end-date-and-time-sign" var="chooseEndDateAndTimeSign"/>
    <fmt:message bundle="${loc}" key="guest.car-catalog.order-popup.make-an-order-request-button" var="makeAnOrderRequestButton"/>
</head>
<body>
        <jsp:useBean id="all_cars" scope="request" type="java.util.List"/>

        <div class="card-group">
        <c:forEach items="${all_cars}" var="car" varStatus="counter">
            <div class="card">
            <img class="card-img-top" src="${car.assetURL}" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">${car.model}</h5>
                <p class="card-text">${car.number}</p>
                <p class="card-text"><small class="text-muted">${car.hourlyCost}</small></p>
            </div>
                <c:if test="${sessionScope.isCustomer == true}">
                    <button type="button" class="btn btn-primary">
                        <a href="home?command=CAR_OCCUPATION&car_id_to_check=${car.carId}"><c:out value="${makeAnOrderButton}"/></a>
                    </button>
                </c:if>
                <c:if test="${sessionScope.isCustomer != true}">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#makeOrderRequestModal">
                        <c:out value="${makeAnOrderButton}"/>
                    </button>
                </c:if>
            </div>
                <c:if test="${counter.index % 2 == 1}"></div><div class="card-group"></c:if>
        </c:forEach>
        </div>
</body>
</html>
