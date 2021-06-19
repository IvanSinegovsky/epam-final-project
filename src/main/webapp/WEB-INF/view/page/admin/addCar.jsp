<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add car</title>
    <%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
    <%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>
</head>

<body>
<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center">New car form tochange</h4>
        <form method="POST" action="home">
            <input type="hidden" name="command" value="ADD_CAR"/>

            <div class="form-group input-group">
                <input name="model"
                       class="form-control"
                       placeholder="tochange car model"
                       type="text">
            </div>
            <div class="form-group input-group">
                <input name="number"
                       class="form-control"
                       placeholder="tochange car number"
                       type="text">
            </div>
            <div class="form-group input-group">
                <input name="hourly_cost"
                       class="form-control"
                       placeholder="tochange car hourly cost"
                       type="number">
            </div>
            <div class="form-group input-group">
                <input name="asset_url"
                       class="form-control"
                       placeholder="tochange car asset url"
                       type="url">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">tochange add</button>
            </div>
        </form>
    </article>
</div>
</body>
</html>
