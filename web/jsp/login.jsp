<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form name="loginForm" method="post" action="controller">
    <input type="hidden" name="command" value="login">
    <p>Логин: <input type="text" name="login" value=""/></p>
    <p>Пароль: <input type="password" name="password" value=""/></p>
    ${errorLoginPassMessage} <br/>
    ${wronaAction} <br/>
    ${nullPage}<br/>
    <input type="submit" value="Log in"/>
</form>
<hr/>

</body>
</html>
