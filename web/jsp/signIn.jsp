<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.signIn" var="signInBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${signInBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Instyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

</head>
<body>

<%@ include file="header.jsp" %>

<div id="login-page" class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${signInBundle}"/></h3>
    </c:if>

    <h1 class="title has-text-centered"><fmt:message key="personal.account.applicant" bundle="${signInBundle}"/></h1>

    <form name="loginForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="LOGIN_POST">
        <input type="hidden" name="referer" value="${referer}">

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="login" bundle="${signInBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text"
                               placeholder="<fmt:message key="login" bundle="${signInBundle}"/>" value="${login}"
                               name="login" maxlength="25" required
                               title="<fmt:message key="max.length" bundle="${signInBundle}"/>"
                        >
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="password" bundle="${signInBundle}"/></label>
            </div>

            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="password"
                               placeholder="<fmt:message key="password" bundle="${signInBundle}"/>"
                               name="password" maxlength="25" required
                               title="<fmt:message key="max.length" bundle="${signInBundle}"/>"
                        >
                        <a href="${pageContext.request.contextPath}/controller?command=forgotten_password_get">
                            <fmt:message key="forgot.login.or.password" bundle="${signInBundle}"/>
                        </a>
                    </div>
                </div>
            </div>

        </div>

        <c:if test="${errorLoginPassMessage!=null}">
            <p class="help is-success"><fmt:message key="${errorLoginPassMessage}" bundle="${signInBundle}"/></p>
        </c:if>

        <div class="field is-horizontal">
            <div class="field-label">
            </div>
            <div class="field-body">
                <div class="field is-grouped">
                    <div class="control is-expanded">
                        <button class="button is-primary is-fullwidth">
                            <fmt:message key="submit.logIn" bundle="${signInBundle}"/>
                        </button>
                    </div>
                    <div class="control is-expanded">
                        <a href="${pageContext.request.contextPath}/controller?command=sign_up_get"
                           class="button is-fullwidth">
                            <fmt:message key="submit.signUp" bundle="${signInBundle}"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        ${confirmed}

        ${wronaAction} <br/>
        ${nullPage}<br/>

    </form>
</div>
<hr/>

</body>
</html>
