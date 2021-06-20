<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Order request list</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <button type="submit" name="command" value="UNDO_ORDER_REQUEST" class="btn btn-outline-success">
            TOCHANGE undo
        </button>

        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th>TOCHANGE order request id</th>
                <th>TOCHANGE car model</th>
                <th>TOCHANGE expected start time</th>
                <th>TOCHANGE expected end time</th>
                <th>TOCHANGE total cost</th>
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
            </tbody>
        </table>
    </form>
</div>
</body>
</html>