<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/view/fragment/navbar.jspf"%>
<%@ include file="/WEB-INF/view/fragment/bootstrapImport.jspf"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<jsp:useBean id="exception_message" scope="request" type="java.lang.String"/>
<h1>
    <c:out value="${exception_message}"/>
</h1>
</body>
</html>