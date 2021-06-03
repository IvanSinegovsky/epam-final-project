<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
</head>
<body>
<%--<div class="card bg-light">
    <form id="registration_form" method="POST" action="home">
        <input type="hidden" name="command" value="REGISTER"/>

        <div class="input-group input-group-lg">
            <span class="input-group-text">Enter your name</span>
            <input type="text"
                   class="form-control"
                   aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-lg"
                   name="name"
                   required/>
        </div>
        <div class="input-group input-group-lg">
            <span class="input-group-text">Enter your lastname</span>
            <input type="text"
                   class="form-control"
                   aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-lg"
                   name="lastname"
                   required/>
        </div>
        <div class="input-group input-group-lg">
            <span class="input-group-text">Enter your email</span>
            <input type="text"
                   class="form-control"
                   aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-lg"
                   name="email"
                   required/>
        </div>
        <div class="input-group input-group-lg">
            <span class="input-group-text">Enter your password</span>
            <input type="password"
                   class="form-control"
                   aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-lg"
                   name="password"
                   required/>
        </div>
        <div class="input-group input-group-lg">
            <span class="input-group-text">Enter your passport number</span>
            <input type="text"
                   class="form-control"
                   aria-label="Sizing example input"
                   aria-describedby="inputGroup-sizing-lg"
                   name="passport_number"
                   required/>
        </div>
        <div>
            <button type="submit" class="btn btn-primary">Register</button>
        </div>
    </form>
</div>--%>


<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center">Create Account</h4>

        <form method="POST" action="home">
            <input type="hidden" name="command" value="REGISTER"/>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="name"
                       class="form-control"
                       placeholder="Name"
                       type="text">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="lastname"
                       class="form-control"
                       placeholder="Lastname"
                       type="text">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email"
                       class="form-control"
                       placeholder="Email address"
                       type="email">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password"
                       class="form-control"
                       placeholder="Password"
                       type="password">
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="passport_number"
                       class="form-control"
                       placeholder="Passport number"
                       type="text">
            </div>

            <%--<div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input class="form-control" placeholder="Repeat password" type="password">
            </div>--%>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>

            <p class="text-center">Have an account? <a href="">Log In</a> </p>
        </form>
    </article>
</div>
</body>
</html>
