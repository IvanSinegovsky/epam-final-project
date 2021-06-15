<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Car catalog</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

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
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#makeOrderRequestModal">
                    <c:out value="${makeAnOrderButton}"/>
                </button>
            </div>
                <c:if test="${counter.index % 2 == 1}"></div><div class="card-group"></c:if>
        </c:forEach>
        </div>

        <div class="modal fade" id="makeOrderRequestModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            <c:out value="${orderDetailsSign}"/>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="form-group row">
                        <label for="start_date_and_time" class="col-2 col-form-label"><c:out value="${chooseStartDateAndTimeSign}"/></label>
                        <div class="col-10">
                            <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="start_date_and_time">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="end_date_and_time" class="col-2 col-form-label"><c:out value="${chooseEndDateAndTimeSign}"/></label>
                        <div class="col-10">
                            <input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="end_date_and_time">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" name="command" value="COMMAND_NAME_TODO_CHANGE" class="btn btn-primary">
                            <c:out value="${makeAnOrderRequestButton}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>
