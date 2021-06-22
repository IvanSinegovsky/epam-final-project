<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Promo code list</title>

    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/newPromoCodePopup.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="admin.promo-codes.add-promo-code-button" var="addPromoCodeButton"/>
    <fmt:message bundle="${loc}" key="admin.promo-codes.disable-promo-code-button" var="disablePromoCodeButton"/>
    <fmt:message bundle="${loc}" key="admin.promo-codes.table.column-name.promo-code-id" var="promoCodeIdColumnName"/>
    <fmt:message bundle="${loc}" key="admin.promo-codes.table.column-name.promo-code" var="promoCodeColumnName"/>
    <fmt:message bundle="${loc}" key="admin.promo-codes.table.column-name.discount" var="discountColumnName"/>
    <fmt:message bundle="${loc}" key="admin.promo-codes.table.column-name.is-active" var="isActiveColumnName"/>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPromoCodeModalModal">
            <c:out value="${addPromoCodeButton}"/>
        </button>
        <button type="submit" name="command" value="DISABLE_PROMO_CODE" class="btn btn-outline-success">
            <c:out value="${disablePromoCodeButton}"/>
        </button>

        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th><c:out value="${promoCodeIdColumnName}"/></th>
                <th><c:out value="${promoCodeColumnName}"/></th>
                <th><c:out value="${discountColumnName}"/></th>
                <th><c:out value="${isActiveColumnName}"/></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="all_promo_codes" scope="request" type="java.util.List"/>
            <c:forEach var="promo_code" items="${all_promo_codes}">
                <tr>
                    <td>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="checkbox" class="form-check-input" name="selected_promo_codes" value="${promo_code.promoCode}">
                            </label>
                        </div>
                    </td>
                    <td><c:out value="${promo_code.promoCodeId}"/></td>
                    <td><c:out value="${promo_code.promoCode}"/></td>
                    <td><c:out value="${promo_code.discount}"/></td>
                    <td><c:out value="${promo_code.isActive}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>