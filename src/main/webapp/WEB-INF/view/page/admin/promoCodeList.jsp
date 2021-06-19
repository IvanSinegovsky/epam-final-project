<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Promo code list</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/newPromoCodePopup.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>
<body>
<div class="container">
    <form method="POST" action="home">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPromoCodeModalModal">
            TOCHANGE add promocode
        </button>
        <button type="submit" name="command" value="DISABLE_PROMO_CODE" class="btn btn-outline-success">
            TOCHANGE disable
        </button>

        <table class="table table-striped">
            <thead>
            <tr>
                <th></th>
                <th>TOCHANGE promoCodeId</th>
                <th>TOCHANGE promoCode</th>
                <th>TOCHANGE discount</th>
                <th>TOCHANGE isActive</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="all_promo_codes" scope="request" type="java.util.List"/>
            <c:forEach var="promo_code" items="${all_promo_codes}">
                <tr>
                    <td><div class="form-check">
                        <label class="form-check-label">
                            <input type="checkbox" class="form-check-input" name="selected_promo_codes" value="${promo_code.promoCode}">
                        </label>
                    </div></td>
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