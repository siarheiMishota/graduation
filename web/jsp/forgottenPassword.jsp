<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.forgottenPassword" var="forgottenPasswordBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${forgottenPasswordBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Instyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

</head>
<body>
<%@ include file="header.jsp" %>

<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.restoring.password" bundle="${forgottenPasswordBundle}"/></h1>

    <form name="forgottenPasswordForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="forgotten_password_post">


        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="email" bundle="${forgottenPasswordBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="email" required value="${email}" maxlength="25"
                               placeholder="<fmt:message key="email" bundle="${forgottenPasswordBundle}"/>"
                               pattern="^([\w\-\.]+)@((\[\d{1,3}\.\d{1,3}\.\d{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|\d{1,3})(\]?)$"
                                title=" <fmt:message key="email.incorrect" bundle="${forgottenPasswordBundle}"/>"
                        >
                    </div>
                    <c:if test="${errors.email!=null}">
                        <fmt:message key="${errors.email}" bundle="${forgottenPasswordBundle}"/>
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
                            <fmt:message key="submit.recovery" bundle="${forgottenPasswordBundle}"/>
                        </button>
                    </div>
                    <div class="control is-expanded">
                        <a href="${pageContext.request.contextPath}/controller?command=login_get"
                           class="button is-fullwidth">
                            <fmt:message key="submit.signIn" bundle="${forgottenPasswordBundle}"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>


        
    </form>

</div>
</body>
</html>
