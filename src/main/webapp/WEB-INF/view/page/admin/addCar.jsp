<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add car</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

    <fmt:message bundle="${loc}" key="admin.add-car.new-car-form-sign" var="newCarFormSign"/>
    <fmt:message bundle="${loc}" key="admin.add-car.car-model-input" var="carModelInput"/>
    <fmt:message bundle="${loc}" key="admin.add-car.car-number-input" var="carNumberInput"/>
    <fmt:message bundle="${loc}" key="admin.add-car.car-hourly-cost-input" var="carHourlyCostInput"/>
    <fmt:message bundle="${loc}" key="admin.add-car.car-asset-url-input" var="carAssetUrlInput"/>
    <fmt:message bundle="${loc}" key="admin.add-car.add-button" var="addButton"/>
</head>

<body>
<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center"><c:out value="${newCarFormSign}"/></h4>
        <form method="POST" action="home">
            <input type="hidden" name="command" value="ADD_CAR"/>

            <div class="form-group input-group">
                <input name="model"
                       class="form-control"
                       placeholder="<c:out value="${carModelInput}"/>"
                       type="text">
            </div>
            <div class="form-group input-group">
                <input name="number"
                       class="form-control"
                       placeholder="<c:out value="${carNumberInput}"/>"
                       type="text">
            </div>
            <div class="form-group input-group">
                <input name="hourly_cost"
                       class="form-control"
                       placeholder="<c:out value="${carHourlyCostInput}"/>"
                       type="number">
            </div>
            <div class="form-group input-group">
                <input name="asset_url"
                       class="form-control"
                       placeholder="<c:out value="${carAssetUrlInput}"/>"
                       type="url">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary"><c:out value="${addButton}"/></button>
            </div>
        </form>
    </article>
</div>
</body>
</html>
