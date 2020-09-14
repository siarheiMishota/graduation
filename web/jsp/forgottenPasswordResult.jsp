<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.forgottenPasswordResult"
               var="forgottenPasswordResultBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${forgottenPasswordResultBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signInStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

</head>
<body>
<%@ include file="header.jsp" %>

<div id="login-page" class="container">
        <h1 class="title has-text-centered"><fmt:message key="label.sent.letter"
                                                         bundle="${forgottenPasswordResultBundle}"/></h1>
</div>
</body>
</html>
