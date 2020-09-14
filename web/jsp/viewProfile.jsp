<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewProfile" var="viewProfileBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${viewProfileBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>

<%@include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.view.user.profile"
                                                     bundle="${viewProfileBundle}"/></h1>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="login" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.login}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="email" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.email}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="surname" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.surname}</label>
            </div>        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="firstName" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.firstName}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="fatherName" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.fatherName}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="passportId" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.passportId}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="birth" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.birth}</label>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="gender" bundle="${viewProfileBundle}"/></label>
            </div>
            <div class="field-label is-normal">
                <label class="label">${user.gender}</label>
            </div>
        </div>
</div>

</body>
</html>
