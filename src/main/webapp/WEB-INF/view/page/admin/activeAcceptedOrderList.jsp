<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Active accepted order list</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.mark-as-complete-button" var="markAsCompleteButton"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.make-repair-bill" var="makeRepairBillButton"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.order-id-column" var="orderIdColumn"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.bill-column" var="billColumn"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.car-id-column" var="carIdColumn"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.admin-accepted-id-column" var="adminAcceptedIdColumn"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.repair-bill-dialog.repair-bill-sign" var="repairBillSign"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.repair-bill-dialog.comment-input" var="commentInput"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.repair-bill-dialog.bill-input" var="billInput"/>
    <fmt:message bundle="${loc}" key="admin.active-accepted-order-list.repair-bill-dialog.create-button" var="createButton"/>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <div style="margin-top: 15px; margin-bottom: 15px">
        <button type="submit" name="command" value="COMPLETE_ACCEPTED_ORDER" class="btn btn-outline-success">
            <c:out value="${markAsCompleteButton}"/>
        </button>
        <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#makeRepairBillModal">
            <c:out value="${makeRepairBillButton}"/>
        </button>
        </div>

        <div class="modal fade" id="makeRepairBillModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            <c:out value="${repairBillSign}"/>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group input-group">
                            <input name="comment"
                                   class="form-control"
                                   placeholder="<c:out value="${commentInput}"/>"
                                   type="text">
                        </div>
                        <div class="form-group input-group">
                            <input name="bill"
                                   class="form-control"
                                   placeholder="<c:out value="${billInput}"/>"
                                   type="number">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" name="command" value="MAKE_REPAIR_BILL" class="btn btn-primary">
                            <c:out value="${createButton}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <div style="margin-left: 20px;margin-right: 20px">
        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th><c:out value="${orderIdColumn}"/></th>
                <th><c:out value="${billColumn}"/></th>
                <th><c:out value="${carIdColumn}"/></th>
                <th><c:out value="${adminAcceptedIdColumn}"/></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="active_accepted_orders" scope="request" type="java.util.List"/>
            <c:forEach var="activeAcceptedOrder" items="${active_accepted_orders}">
                <tr>
                    <td><div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" name="selected_accepted_orders" value="${activeAcceptedOrder}">
                        </label>
                    </div></td>
                    <td><c:out value="${activeAcceptedOrder.orderId}"/></td>
                    <td><c:out value="${activeAcceptedOrder.bill}"/></td>
                    <td><c:out value="${activeAcceptedOrder.carId}"/></td>
                    <td><c:out value="${activeAcceptedOrder.adminUserAcceptedId}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>