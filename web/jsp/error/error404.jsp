<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.error.error404" var="error404Bundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${error404Bundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>

<div class="container">
    <h3 class="title has-text-centered"><fmt:message key="label.error404" bundle="${error404Bundle}"/></h3>

    <img class="image has-image-centered" src="${pageContext.request.contextPath}/pictures/errorLog.jpg" alt="" width="500"/>

</div>

</body>
</html>
