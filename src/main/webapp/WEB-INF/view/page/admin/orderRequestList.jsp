<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
    <title>Order request list</title>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <button type="submit" name="command" value="ACCEPT_ORDER" class="btn btn-outline-success">
            <fmt:message key="admin.order-request-list.accept-button" bundle="${loc}" />
        </button>
        <button type="submit" name="command" value="REJECT_ORDER" class="btn btn-outline-warning">
            <fmt:message key="admin.order-request-list.reject-button" bundle="${loc}" />
        </button>
        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.request-id" bundle="${loc}" /></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.customer-rate" bundle="${loc}" /></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.car-model" bundle="${loc}" /></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.expected-start-time" bundle="${loc}" /></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.expected-end-time" bundle="${loc}" /></th>
                <th><fmt:message key="admin.order-request-list.table.column-name.total-cost" bundle="${loc}" /></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="orderRequestInfos" scope="request" type="java.util.List"/>
            <c:forEach var="orderRequestInfo" items="${orderRequestInfos}">
                <tr>
                    <td><div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" name="selected" value="${orderRequestInfo}">
                        </label>
                    </div></td>
                    <td><c:out value="${orderRequestInfo.orderRequestId}"/></td>
                    <td><c:out value="${orderRequestInfo.customerRate}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedCarModel}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedStartTime}"/></td>
                    <td><c:out value="${orderRequestInfo.expectedEndTime}"/></td>
                    <td><c:out value="${orderRequestInfo.totalCost}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>