<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.viewNews" var="viewNewsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${viewNewsBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>
<h1 class="title has-text-centered"><fmt:message key="label.view.news" bundle="${viewNewsBundle}"/></h1>

<div id="login-page" class="container">

        <c:if test="${message!=null}">
            <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${viewNewsBundle}"/></h3>
        </c:if>

        <c:if test="${user!=null}">
            <h3 class="title has-text-centered"><fmt:message key="creator" bundle="${viewNewsBundle}"/></h3>

            <div class="container level-item">
                <table class="table is-bordered">
                    <tbody>
                    <tr>
                        <td>
                            <fmt:message key="login" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.login}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="email" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.email}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="surname" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.surname}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="firstName" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.firstName}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="fatherName" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.fatherName}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="gender" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                            <fmt:message key="${user.gender}" bundle="${viewNewsBundle}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="birth" bundle="${viewNewsBundle}"/>
                        </td>
                        <td>
                                ${user.birth}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <br>
        </c:if>

        <h3 class="title has-text-centered"><fmt:message key="news.name.en" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="3" cols="20">${news.nameEn}</textarea>
        <br>

        <h3 class="title has-text-centered"><fmt:message key="news.name.ru" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="3" cols="20">${news.nameRu}</textarea>
        <br>

        <h3 class="title has-text-centered"><fmt:message key="brief.description.en" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="5" cols="20">${news.briefDescriptionEn}</textarea>
        <br>

        <h3 class="title has-text-centered"><fmt:message key="brief.description.ru" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="5" cols="20">${news.briefDescriptionRu}</textarea>
        <br>

        <h3 class="title has-text-centered"><fmt:message key="english.variable" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="15" cols="20">${news.englishVariable}</textarea>
        <br>

        <h3 class="title has-text-centered"><fmt:message key="russian.variable" bundle="${viewNewsBundle}"/></h3>
        <textarea class="textarea" rows="15" cols="20">${news.russianVariable}</textarea>
        <br>
</div>
</body>
</html>
