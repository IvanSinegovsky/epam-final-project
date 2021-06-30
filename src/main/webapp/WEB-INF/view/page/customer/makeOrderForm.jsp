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
    <fmt:message bundle="${loc}" key="customer.make-order-form.current-car-is-not-occupied-sign" var="currentCarIsNotOccupiedSign"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.car-occupation-start-time-sign" var="carOccupationStartTimeSign"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.car-occupation-end-time-sign" var="carOccupationEndTimeSign"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.promo-code-input" var="promoCodeInput"/>
    <fmt:message bundle="${loc}" key="customer.make-order-form.submit-button" var="submitButton"/>
</head>

<body>
<jsp:useBean id="car_occupation" scope="request" type="java.util.List"/>


<div class="row">
    <div class="col">
        <div>
            <div style="margin-left: 10%;margin-top: 25px;margin-bottom: 25px">
                <h3><c:out value="${carOccupationSign}"/>:</h3>
            </div>

            <c:if test="${empty car_occupation}">
                <div style="margin-left: 10%;">
                    <h5>
                        <i><c:out value="${currentCarIsNotOccupiedSign}"/></i>
                    </h5>
                </div>
            </c:if>
            <c:if test="${!empty car_occupation}">
                <div style="margin-left: 10%;margin-right: 10%">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><c:out value="${carOccupationStartTimeSign}"/></th>
                        <th scope="col"><c:out value="${carOccupationEndTimeSign}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${car_occupation}" var="occupation" varStatus="counter">
                        <tr>
                            <div>
                                <td><c:out value="${occupation.startTime}"/></td>
                                <td><c:out value="${occupation.endTime}"/></td>
                            </div>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </div>
            </c:if>
        </div>
    </div>

    <div class="col">
        <div style="margin-left: 10%;margin-top: 25px">
            <h3><c:out value="${selectTimeIntervalSign}:"/></h3>
            <form method="POST" action="http://localhost:8080/home?command=MAKE_ORDER_REQUEST">
                <jsp:useBean id="car_id_to_check" scope="request" type="java.lang.Long"/>
                <input type="hidden" name="car_id" value="${car_id_to_check}"/>
                <div class="form-group row w-75" style="margin-top: 30px">
                    <label for="start_date_and_time"><c:out value="${chooseRideStartTimeInput}:"/></label>
                    <div class="col-10">
                        <input class="form-control" type="datetime-local" id="start_date_and_time" name="start_date_and_time">
                    </div>
                </div>
                <div class="form-group row w-75">
                    <label for="end_date_and_time"><c:out value="${chooseRideEndTimeInput}:"/></label>
                    <div class="col-10">
                        <input class="form-control" type="datetime-local" id="end_date_and_time" name="end_date_and_time">
                    </div>
                </div>
                <div class="form-group row w-75">
                    <label for="promo_code"><c:out value="${promoCodeInput}:"/></label>
                    <div class="col-10">
                        <input class="form-control" type="text" id="promo_code" name="promo_code">
                    </div>
                </div>
                <div class="form-group" style="margin-left: 10%">
                    <button type="submit" class="btn btn-primary"><c:out value="${submitButton}"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>