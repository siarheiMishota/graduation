<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.activationForgottenPassword" var="activationForgottenPasswordBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${activationForgottenPasswordBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signInStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

</head>
<body>
<%@ include file="header.jsp" %>

<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.activation.new.password" bundle="${activationForgottenPasswordBundle}"/></h1>

    <form name="activationForgottenPasswordForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="activation_forgotten_password_post">
        <input type="hidden" name="activationCode" value="${activationCode}">
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="new.password" bundle="${activationForgottenPasswordBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="password" name="password" required value="${password}" maxlength="25"
                               placeholder="<fmt:message key="new.password" bundle="${activationForgottenPasswordBundle}"/>"
                               pattern="^(?=.*[A-Za-z а-я А-Я])(?=.*\d)[A-Za-z а-я А-Я\d]{8,}$"
                               title="<fmt:message key="title.password" bundle="${activationForgottenPasswordBundle}"/>"
                        >
                    </div>
                    <c:if test="${errors.password!=null}">
                        <fmt:message key="${errors.password}" bundle="${activationForgottenPasswordBundle}"/>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label">
            </div>
            <div class="field-body">
                <div class="field is-grouped">
                    <div class="control is-expanded">
                        <button class="button is-primary is-fullwidth">
                            <fmt:message key="save.new.password" bundle="${activationForgottenPasswordBundle}"/>
                        </button>
                    </div>

                </div>
            </div>
        </div>



    </form>

</div>
</body>
</html>
