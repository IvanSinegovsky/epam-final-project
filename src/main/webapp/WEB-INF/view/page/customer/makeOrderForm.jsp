<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Order form</title>

    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="resources" var="loc"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.car-occupation-time-sign" var="carOccupationSign"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.select-not-occupied-time-interval-sign" var="selectTimeIntervalSign"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.choose-ride-start-time" var="chooseRideStartTimeInput"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.choose-ride-end-time" var="chooseRideEndTimeInput"/>
</head>

<body>
<h3><c:out value="${carOccupationSign}"/></h3>
<jsp:useBean id="car_occupation" scope="request" type="java.util.List"/>
<div class="card-group">
    <c:forEach items="${car_occupation}" var="occupation" varStatus="counter">
    <div>
        <c:out value="${occupation.startTime}"/>
        <br/>
        <c:out value="${occupation.endTime}"/>
    </div>
    </c:forEach>
</div>

<br/>
<h4><c:out value="${selectTimeIntervalSign}"/></h4>
    <form method="POST" action="http://localhost:8080/home?command=MAKE_ORDER_REQUEST">
        <div class="form-group row">
           <label for="start_date_and_time" class="col-2 col-form-label"><c:out value="${chooseRideStartTimeInput}"/></label>
           <div class="col-10">
               <input class="form-control" type="datetime-local" id="start_date_and_time" name="start_date_and_time">
           </div>
        </div>
        <div class="form-group row">
            <label for="end_date_and_time" class="col-2 col-form-label"><c:out value="${chooseRideEndTimeInput}"/></label>
            <div class="col-10">
                <input class="form-control" type="datetime-local" id="end_date_and_time" name="end_date_and_time">
            </div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">TOCHANGE_SUBMIT</button>
        </div>
    </form>
</body>
</html>