<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="prop.internalization.jsp.uploadPhoto" var="uploadPhotoBundle"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${uploadPhotoBundle}"/></title>
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${uploadPhotoBundle}"/></h3>
    </c:if>

    <form action="${pageContext.request.contextPath}/upload/add_photo" enctype="multipart/form-data" method="post">
        <h1 class="title has-text-centered"><fmt:message key="label.uploading"
                                                         bundle="${uploadPhotoBundle}"/></h1>
        <h5 class="title has-text-centered">
            <c:if test="${errors.photo!=null}">
                <fmt:message key="${errors.photo}" bundle="${uploadPhotoBundle}"/>
            </c:if>
        </h5>
        <div class="file is-primary">
            <label class="file-label">
                <input class="file-input" type="file" name="image">
                <span class="file-cta">
                    <span class="file-icon">
                        <em class="fas fa-upload"></em>
                    </span>
                    <span class="file-label">
                        <fmt:message key="choose.file"
                                     bundle="${uploadPhotoBundle}"/>
                    </span>
                </span>

            </label>
            <div class="field is-horizontal">
                <button class="button is-primary is-fullwidth  margin-right">
                    <fmt:message key="save"
                                 bundle="${uploadPhotoBundle}"/>
                </button>
            </div>

        </div>
    </form>
</div>
</body>
</html>
