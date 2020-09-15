<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="signUpBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${signUpBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>

<%@include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.signUp" bundle="${signUpBundle}"/></h1>
    <form name="signUpForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="sign_up_post">

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="login" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text"
                               placeholder="<fmt:message key="login"  bundle="${signUpBundle}"/>"
                               name="login" maxlength="25" required value="${login}">
                        <c:if test="${errors.login!=null}">
                            <fmt:message key="${errors.login}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="email" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="email" required value="${email}" maxlength="50"
                               placeholder="<fmt:message key="email" bundle="${signUpBundle}"/>"
                               pattern="^([\w\-\.]+)@((\[\d{1,3}\.\d{1,3}\.\d{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|\d{1,3})(\]?)$"
                               title="<fmt:message key="${errors.email}" bundle="${signUpBundle}"/>"
                        >
                    </div>
                    <c:if test="${errors.email!=null}">
                        <fmt:message key="${errors.email}" bundle="${signUpBundle}"/>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="password" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="password" name="password" required maxlength="25"
                               placeholder="<fmt:message key="password" bundle="${signUpBundle}"/>"
                               pattern="^(?=.*[A-Za-z а-я А-Я])(?=.*\d)[A-Za-z а-я А-Я\d]{8,}$"
                               title="<fmt:message key="simple.password" bundle="${signUpBundle}"/>"
                        >
                        <c:if test="${errors.password!=null}">
                            <fmt:message key="${errors.password}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="surname" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" maxlength="25" name="surname" required value="${surname}"
                               placeholder="<fmt:message key="surname" bundle="${signUpBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${signUpBundle}"/>"
                        >

                        <c:if test="${errors.surname!=null}">
                            <fmt:message key="${errors.surname}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="firstName" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="firstName" required value="${firstName}" maxlength="25"
                               placeholder="<fmt:message key="firstName" bundle="${signUpBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${signUpBundle}"/>"
                        >
                        <c:if test="${errors.firstName!=null}">
                            <fmt:message key="${errors.firstName}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="fatherName" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="fatherName" required value="${fatherName}" maxlength="25"
                               placeholder="<fmt:message key="fatherName" bundle="${signUpBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${signUpBundle}"/>"
                        >
                        <c:if test="${errors.fatherName!=null}">
                            <fmt:message key="${errors.fatherName}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="passportId" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="passportId" required value="${passportId}" maxlength="25"
                               placeholder="<fmt:message key="passportId" bundle="${signUpBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${signUpBundle}"/>"
                        >
                        <c:if test="${errors.passportId!=null}">
                            <fmt:message key="${errors.passportId}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="birth" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="date"
                               placeholder="<fmt:message key="birth" bundle="${signUpBundle}"/>"
                               name="birth" required value="${birth}">
                        <c:if test="${errors.birth!=null}">
                            <fmt:message key="${errors.birth}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="gender" bundle="${signUpBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <label class="radio">
                            <input class="radio" type="radio" name="gender" required value="male">
                            <fmt:message key="male" bundle="${signUpBundle}"/>
                        </label>

                        <label class="radio">
                            <input class="radio" type="radio" name="gender" required value="female">
                            <fmt:message key="female" bundle="${signUpBundle}"/>
                        </label>
                        <c:if test="${errors.gender!=null}">
                            <fmt:message key="${errors.gender}" bundle="${signUpBundle}"/>
                        </c:if>
                    </div>
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
                            <fmt:message key="submit.signUp" bundle="${signUpBundle}"/>
                        </button>
                    </div>
                    <div class="control is-expanded">
                        <a href="${pageContext.request.contextPath}/controller?command=login_get"
                           class="button is-fullwidth">
                            <fmt:message key="submit.login" bundle="${signUpBundle}"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
