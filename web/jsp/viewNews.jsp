<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewNews" var="viewNewsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${viewNewsBundle}"/></title>

</head>
<body>
<%@ include file="header.jsp" %>
<h1 class="title has-text-centered"><fmt:message key="label.view.news" bundle="${viewNewsBundle}"/></h1>

<div id="login-page" class="container">

    <h5 class="title has-text-centered">
            <c:if test="${language.equals('en')}">${news.nameEn}</c:if>
            <c:if test="${language.equals('ru')}">${news.nameRu}</c:if>
    </h5>

    <div class="field is-horizontal ">
        <div class="field-body">
            <div class="field">
                <div class="control">
                            <textarea readonly class="textarea" rows="10" cols="15"><c:if
                                    test="${language.equals('en')}">${news.englishVariable}</c:if><c:if
                                    test="${language.equals('ru')}">${news.russianVariable}</c:if></textarea>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
