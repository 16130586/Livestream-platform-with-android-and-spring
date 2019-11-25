<!DOCTYPE HTML>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Forgot Password</title>
</head>
<body>
<h1>Renew password</h1>
<c:if test="${empty isSuccess}">
    <c:if test="${isCorrectInfo}">
    <form action="/renewPassword/${userId}" method="post" onsubmit="return checkPassword()">
        <div style="margin: 10px">
            <label style="padding: 5px">New password:
                <input id="new-password" name="newPassword" style="padding: 5px" type="password" placeholder="Enter new password..." required>
            </label>
        </div>
        <div style="margin: 10px">
            <label style="padding: 5px">Confirm password:
                <input id="re-password" name="reNewPassword" style="padding: 5px" type="password" placeholder="Confirm new password..." required>
            </label>
        </div>
        <div style="margin: 10px">
            <input style="padding: 5px" type="submit" value="submit">
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