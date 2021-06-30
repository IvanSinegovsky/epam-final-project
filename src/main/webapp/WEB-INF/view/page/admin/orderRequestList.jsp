<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Order request list</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="admin.order-request-list.accept-button" var="acceptButton"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.reject-button" var="rejectButton"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.reject-dialog.type-rejection-reason-sign" var="rejectionReasonSign"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.reject-dialog.rejection-reason-input" var="rejectionReasonInput"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.reject-dialog.reject-order-request-button" var="rejectOrderRequestButton"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.request-id" var="requestIdColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.customer-rate" var="customerRateColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.car-model" var="carModelColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.expected-start-time" var="expectedStartTimeColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.expected-end-time" var="expectedEndTimeColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.total-cost" var="totalCostColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.column-name.customer-statistics" var="customerStatisticsColumnName"/>
    <fmt:message bundle="${loc}" key="admin.order-request-list.table.check-button" var="checkButton"/>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <div style="margin-top: 15px; margin-bottom: 15px">
        <button type="submit" name="command" value="ACCEPT_ORDER" class="btn btn-outline-success">
            <c:out value="${acceptButton}"/>
        </button>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#rejectionReasonModal">
            <c:out value="${rejectButton}"/>
        </button>
        </div>

        <div class="modal fade" id="rejectionReasonModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            <c:out value="${rejectionReasonSign}"/>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group input-group">
                            <input name="rejectionReason"
                                   class="form-control"
                                   placeholder="<c:out value="${rejectionReasonInput}"/>"
                                   type="text">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" name="command" value="REJECT_ORDER" class="btn btn-primary">
                            <c:out value="${rejectOrderRequestButton}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th><c:out value="${requestIdColumnName}"/></th>
                <th><c:out value="${customerRateColumnName}"/></th>
                <th><c:out value="${carModelColumnName}"/></th>
                <th><c:out value="${expectedStartTimeColumnName}"/></th>
                <th><c:out value="${expectedEndTimeColumnName}"/></th>
                <th><c:out value="${totalCostColumnName}"/></th>
                <th><c:out value="${customerStatisticsColumnName}"/></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="order_request_infos" scope="request" type="java.util.List"/>
            <c:forEach var="orderRequestInfo" items="${order_request_infos}">
                <tr>
                    <td><div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" name="selected_accepted_orders" value="${orderRequestInfo}">
                        </label>
                    </div></td>
                    <td><c:out value="${orderRequestInfo.orderRequestId}"/></td>
                    <td><c:out value="${orderRequestInfo.customerRate}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedCarModel}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedStartTime}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedEndTime}"/></td>
                    <td><c:out value="${orderRequestInfo.totalCost}"/></td>
                    <td>
                        <a class="btn btn-primary"
                           href="home?command=CHECK_CUSTOMER_STATISTICS&order_request_id=${orderRequestInfo.orderRequestId}"
                           role="button"><c:out value="${checkButton}"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </form>
</div>
</body>
</html>