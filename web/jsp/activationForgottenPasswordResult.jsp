<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.activationForgottenPasswordResult"
               var="activationForgottenPasswordResultBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${activationForgottenPasswordResultBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Instyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

</head>
<body>
<%@ include file="header.jsp" %>

<div id="login-page" class="container">
    <c:if test="${errors.activationCode==null}">
        <h1 class="title has-text-centered"><fmt:message key="label.password.updated"
                                                         bundle="${activationForgottenPasswordResultBundle}"/></h1>
    </c:if>

    <c:if test="${errors.activationCode!=null}">
        <h1 class="title has-text-centered"><fmt:message key="${errors.activationCode}"
                                                         bundle="${activationForgottenPasswordResultBundle}"/></h1>
    </c:if>
</div>
</body>
</html>
