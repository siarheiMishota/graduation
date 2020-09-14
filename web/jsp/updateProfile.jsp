<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.updateProfile" var="updateProfileBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${updateProfileBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>

<%@include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.update.user.profile"
                                                     bundle="${updateProfileBundle}"/></h1>
    <form name="updateUserProfileForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="update_profile_post">

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="login" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text"
                               placeholder="<fmt:message key="login"  bundle="${updateProfileBundle}"/>"
                               name="login" maxlength="25" required value="${user.login}">
                        <c:if test="${errors.login!=null}">
                            <fmt:message key="${errors.login}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="email" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="email" required value="${user.email}" maxlength="50"
                               placeholder="<fmt:message key="email" bundle="${updateProfileBundle}"/>"
                               pattern="^([\w\-\.]+)@((\[\d{1,3}\.\d{1,3}\.\d{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|\d{1,3})(\]?)$"
                               title="<fmt:message key="${errors.email}" bundle="${updateProfileBundle}"/>"
                        >
                    </div>
                    <c:if test="${errors.email!=null}">
                        <fmt:message key="${errors.email}" bundle="${updateProfileBundle}"/>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="surname" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" maxlength="25" name="surname" required value="${user.surname}"
                               placeholder="<fmt:message key="surname" bundle="${updateProfileBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${updateProfileBundle}"/>"
                        >

                        <c:if test="${errors.surname!=null}">
                            <fmt:message key="${errors.surname}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="firstName" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="firstName" required value="${user.firstName}"
                               maxlength="25"
                               placeholder="<fmt:message key="firstName" bundle="${updateProfileBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${updateProfileBundle}"/>"
                        >
                        <c:if test="${errors.firstName!=null}">
                            <fmt:message key="${errors.firstName}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="fatherName" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="fatherName" required value="${user.fatherName}"
                               maxlength="25"
                               placeholder="<fmt:message key="fatherName" bundle="${updateProfileBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${updateProfileBundle}"/>"
                        >
                        <c:if test="${errors.fatherName!=null}">
                            <fmt:message key="${errors.fatherName}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="passportId" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text" name="passportId" required value="${user.passportId}"
                               maxlength="25"
                               placeholder="<fmt:message key="passportId" bundle="${updateProfileBundle}"/>"
                               title="<fmt:message key="max.length" bundle="${updateProfileBundle}"/>"
                        >
                        <c:if test="${errors.passportId!=null}">
                            <fmt:message key="${errors.passportId}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="birth" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="date"
                               placeholder="<fmt:message key="birth" bundle="${updateProfileBundle}"/>"
                               name="birth" required value="${user.birth}">
                        <c:if test="${errors.birth!=null}">
                            <fmt:message key="${errors.birth}" bundle="${updateProfileBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"><fmt:message key="gender" bundle="${updateProfileBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <label class="radio">
                            <input class="radio" type="radio" name="gender" value="MALE" required
                            <c:if test="${user.gender.toString().equals('MALE')}">
                                   checked>
                            </c:if>
                            <fmt:message key="male" bundle="${updateProfileBundle}"/>
                        </label>

                        <label class="radio">
                            <input class="radio" type="radio" name="gender" value="FEMALE" required
                            <c:if test="${user.gender.toString().equals('FEMALE')}">
                                   checked>
                            </c:if>>
                            <fmt:message key="female" bundle="${updateProfileBundle}"/>
                        </label>
                        <c:if test="${errors.gender!=null}">
                            <fmt:message key="${errors.gender}" bundle="${updateProfileBundle}"/>
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
                            <fmt:message key="submit.update" bundle="${updateProfileBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
