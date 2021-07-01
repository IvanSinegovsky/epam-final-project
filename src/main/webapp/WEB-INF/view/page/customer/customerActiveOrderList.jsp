<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Order request list</title>

    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="customer.active-order-list.undo-button" var="undoButton"/>
    <fmt:message bundle="${loc}" key="customer.active-order-list.column-name.order-request-id" var="orderRequestIdColumnName"/>
    <fmt:message bundle="${loc}" key="customer.active-order-list.column-name.car-model" var="carModelColumnName"/>
    <fmt:message bundle="${loc}" key="customer.active-order-list.column-name.expected-start-time" var="expectedStartTimeColumnName"/>
    <fmt:message bundle="${loc}" key="customer.active-order-list.column-name.expected-end-time" var="expectedEndTimeColumnName"/>
    <fmt:message bundle="${loc}" key="customer.active-order-list.column-name.total-cost" var="totalCostColumnName"/>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <div style="margin-top: 15px; margin-bottom: 15px">
            <button type="submit" name="command" value="UNDO_ORDER_REQUEST" class="btn btn-outline-danger">
                <c:out value="${undoButton}"/>
            </button>
        </div>

        <div style="margin-left: 20px">
        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th><c:out value="${orderRequestIdColumnName}"/></th>
                <th><c:out value="${carModelColumnName}"/></th>
                <th><c:out value="${expectedStartTimeColumnName}"/></th>
                <th><c:out value="${expectedEndTimeColumnName}"/></th>
                <th><c:out value="${totalCostColumnName}"/></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="order_request_infos" scope="request" type="java.util.List"/>
            <c:forEach var="orderRequestInfo" items="${order_request_infos}">
                <tr>
                    <td><div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" name="selected_order_requests" value="${orderRequestInfo}">
                        </label>
                    </div></td>
                    <td><c:out value="${orderRequestInfo.orderRequestId}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedCarModel}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedStartTime}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedEndTime}"/></td>
                    <td><c:out value="${orderRequestInfo.totalCost}"/></td>
                </tr>
            </c:forEach>
            </tbody
        </div>
        </table>
    </form>
</div>
</body>
</html>