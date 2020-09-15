<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.deletePhotos" var="deletePhotosBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${deletePhotosBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}"
                                                         bundle="${deletePhotosBundle}"/></h3>
    </c:if>
    <h1 class="title has-text-centered"><fmt:message key="label.delete.photos"
                                                     bundle="${deletePhotosBundle}"/></h1>
    <form name="deletePhotosForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="delete_photos_post">
        <section class="section">
            <div class="container level-item">
                <br>
                <table class="table is-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="choice" bundle="${deletePhotosBundle}"/></th>
                        <th><fmt:message key="photo" bundle="${deletePhotosBundle}"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${!photos.isEmpty()}">
                        <c:forEach var="photo" items="${photos}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="photo"
                                           value="${photo}">
                                </td>
                                <td>
                                    <figure class="image">
                                        <img src="${pageContext.request.contextPath}/images/${photo}" height="60"
                                             width="60" alt="photo">
                                    </figure>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </section>

        <div class="field is-horizontal">
            <div class="field-label">
            </div>
            <div class="field-body">
                <div class="field is-grouped">
                    <div class="control is-expanded">
                        <button class="button is-primary is-fullwidth">
                            <fmt:message key="delete" bundle="${deletePhotosBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
