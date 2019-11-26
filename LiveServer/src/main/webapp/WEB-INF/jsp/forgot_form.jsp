<!DOCTYPE HTML>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>4T-Forgot Password</title>
</head>
<body style="
    background-image: url(https://gitlab.com/aspiration2017/wiki/wikis/uploads/06cc366583afba2c7faf23a0ac3aba79/signup-bg.jpg);
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center;">
<div style="margin-top: 100px">
<div style="width: 600px; margin: auto; background: #fff; border-radius: 10px">
<div style="width: 400px; padding: 60px 0; margin: auto">
<h1 style="line-height: 1.66;
    margin: 0;
    padding: 0;
    font-weight: 900;
    color: #222;
    font-family: 'Montserrat';
    font-size: 31px;
    text-transform: uppercase;
    text-align: center;
    margin-bottom: 40px;">NEW PASSWORD</h1>
<c:if test="${empty isSuccess}">
    <c:if test="${isCorrectInfo}">
    <form action="/renewPassword/${userId}" method="post" onsubmit="return checkPassword()">
        <div style="overflow: hidden;margin-bottom: 20px;">
            <input style="width: 100%;
                            border: 1px solid #ebebeb;
                            border-radius: 5px;
                            padding: 17px 20px;
                            box-sizing: border-box;
                            font-size: 14px;
                            font-weight: 500;
                            color: #222;" name="newPassword" type="password" placeholder="New Password" required>
        </div>
        <div style="overflow: hidden;margin-bottom: 20px;">
            <input style="width: 100%;
                            border: 1px solid #ebebeb;
                            border-radius: 5px;
                            padding: 17px 20px;
                            box-sizing: border-box;
                            font-size: 14px;
                            font-weight: 500;
                            color: #222;" name="reNewPassword" type="password" placeholder="Confirm New Password" required>
        </div>
        <div style="overflow: hidden;margin-bottom: 20px;">
            <button style="background-image: linear-gradient(to left, #74ebd5, #9face6);
                            padding: 17px 20px;
                            box-sizing: border-box;
                            font-size: 14px;
                            font-weight: 700;
                            color: #fff;
                            text-transform: uppercase;
                            border: none;
                            width: 100%; cursor: pointer" type="submit">submit</button>
        </div>
    </form>
    </c:if>
    <c:if test="${!isCorrectInfo}">
    <h3>Invalid link, please try again!</h3>
    </c:if>
</c:if>
<c:if test="${isSuccess}">
    <h3>Re new password success!</h3>
</c:if>
</div>
</div>
</div>
<script>
    function checkPassword() {
        var newPassword = document.getElementById("new-password").value;
        var rePassword = document.getElementById("re-password").value;
        if (newPassword != rePassword) {
            alert("new password & confirm password must be same!")
            return false;
        }
        if (newPassword.length < 6) {
            alert("new password must be greater than 6 character !")
            return false;
        }
        return true;
    }

</script>
</body>

</html>