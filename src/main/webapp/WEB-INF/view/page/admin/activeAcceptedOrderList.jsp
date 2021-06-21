<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Active accepted order list</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <button type="submit" name="command" value="COMPLETE_ACCEPTED_ORDER" class="btn btn-outline-success">
            tochange Mark as complete
        </button>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#makeRepairBillModal">
            TOCHANGE Make repair bill
        </button>

        <div class="modal fade" id="makeRepairBillModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">
                            tochange Repair bill
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group input-group">
                            <input name="comment"
                                   class="form-control"
                                   placeholder="tochange comment"
                                   type="text">
                        </div>
                        <div class="form-group input-group">
                            <input name="bill"
                                   class="form-control"
                                   placeholder="tochange bill"
                                   type="number">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" name="command" value="MAKE_REPAIR_BILL" class="btn btn-primary">
                            tochange Make repair bill
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th>TOCHANGE order id</th>
                <th>TOCHANGE bill</th>
                <th>TOCHANGE car id</th>
                <th>TOCHANGE admin accepted id</th>
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