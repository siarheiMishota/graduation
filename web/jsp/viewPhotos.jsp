<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewPhotos" var="viewPhotosBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${viewPhotosBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}"
                                                         bundle="${viewPhotosBundle}"/></h3>
    </c:if>
    <h1 class="title has-text-centered">
        <fmt:message key="label.view.photos" bundle="${viewPhotosBundle}"/>
    </h1>

    <section class="section">
        <div class="container level-item">
            <div class="columns is-multiline">
                <c:forEach var="photo" items="${photos}">
                    <div class="column is-3">
                        <figure class="image">
                            <img src="${pageContext.request.contextPath}/images/${photo}" alt="photo">
                        </figure>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</div>

</body>
</html>
