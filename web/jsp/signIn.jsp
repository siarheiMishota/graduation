<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="prop.internalization.jsp.signIn" var="rb"/>
<fmt:setLocale value="ru" scope="session"/>
<html>
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
</head>
<body>

<%@ include file="header.jsp"%>

<form name="loginForm" method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="login">
    ${confirmed}
    <p><fmt:message key="login" bundle="${rb}"/> <input type="text" name="login" value=""/></p>
    <p><fmt:message key="password" bundle="${rb}"/> <input type="password" name="password" value=""/></p>
    ${errorLoginPassMessage} <br/>
    ${wronaAction} <br/>
    ${nullPage}<br/>
    <input type="submit" value=" <fmt:message key="submit.logIn" bundle="${rb}"/>">
    <a href="${pageContext.request.contextPath}/jsp/signUp.jsp" >aa </a>
</form>
<hr/>

</body>
</html>
