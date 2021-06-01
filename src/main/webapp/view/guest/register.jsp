<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Register x2</title>
</head>
<body>
<div class="welcomeform">
    <form id="registration_form" method="POST" action="home">
        <input type="hidden" name="command" value="REGISTER"/>

        <div class="field">
            <label for="first_name">
                Enter your name
                <input type="text" name="name" value="" required />
            </label>
        </div>
        <div class="field">
            <label for="last_name">
                Enter your lastname
                <input type="text" name="lastname" value="" required />
            </label>
        </div>
        <div class="field">
            <label for="email">
                Enter your email
                <input type="text" name="email" value="" required />
            </label>
        </div>
        <div class="field">
            <label for="password">
                Enter your password
                <input type="password" name="password" value="" required />
            </label>
        </div>
        <div class="field">
            <label for="passport_number">
                Enter your passport number
                <input type="text" name="passport_number" value="" required />
            </label>
        </div>
        <div>
            <button type="submit">Register</button>
        </div>
    </form>
</div>
</body>
</html>
