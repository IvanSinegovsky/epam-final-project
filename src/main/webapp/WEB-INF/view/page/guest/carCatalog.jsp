<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="car" uri="carrentalapp" %>
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
        <jsp:useBean id="all_cars" scope="request" type="java.util.List"/>
        <div class="row" style="width: 1100px;margin-left: 70px;margin-top: 15px">
            <c:if test="${all_cars.size() > 0}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(0).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(0).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(0).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(0).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${all_cars.size() > 1}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(1).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(1).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(1).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(1).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${all_cars.size() > 2}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(2).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(2).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(2).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(2).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
        </div>
        <br/>
        <div class="row" style="width: 1100px;margin-left: 70px;">
            <c:if test="${all_cars.size() > 3}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(3).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(3).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(3).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(3).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${all_cars.size() > 4}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(4).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(4).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(4).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(4).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${all_cars.size() > 5}">
            <div class="col-sm-4">
                <div class="card">
                    <img class="card-img-top" src="${all_cars.get(5).assetURL}" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">${all_cars.get(5).model}</h5>
                        <p class="card-text"><small class="text-muted">${all_cars.get(5).hourlyCost}</small></p>
                        <c:if test="${sessionScope.isCustomer == true}">
                            <button type="button" class="btn btn-primary">
                                <a href="home?command=CAR_OCCUPATION&car_id_to_check=${all_cars.get(5).carId}"><c:out value="${makeAnOrderButton}"/></a>
                            </button>
                        </c:if>
                        <c:if test="${sessionScope.isCustomer != true}">
                            <%@ include file="/WEB-INF/view/fragment/loginPopup.jspf"%>
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#loginModal">
                                <c:out value="${makeAnOrderButton}"/>
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            </c:if>
        </div>

        <div class="d-flex justify-content-center"style="margin-top: 20px;">
            <car:pagination command="${command}" currentPage="${currentPage}" lastPage="${lastPage}"/>
        </div>
</body>
</html>
