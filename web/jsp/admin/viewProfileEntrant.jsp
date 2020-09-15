<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.viewProfileEntrant" var="viewProfileEntrantBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${viewProfileEntrantBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>

<form name="mainForm" method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="admin_view_profile_entrant_post">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${viewProfileEntrantBundle}"/></h3>
    </c:if>
    <br>

    <div class="container level-item">
        <table class="table is-bordered">
            <tbody>
            <tr>
                <td>
                    <fmt:message key="id" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.id}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="login" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.login}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="email" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.email}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="surname" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.surname}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="firstName" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.firstName}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="fatherName" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.fatherName}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="gender" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    <fmt:message key="${entrant.user.gender}" bundle="${viewProfileEntrantBundle}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="birth" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.birth}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="passportId" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.passportId}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="confirmed" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    <fmt:message key="${entrant.user.confirmed}" bundle="${viewProfileEntrantBundle}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="role" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    <fmt:message key="${entrant.user.role}" bundle="${viewProfileEntrantBundle}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="activationCode" bundle="${viewProfileEntrantBundle}"/>
                </td>
                <td>
                    ${entrant.user.activationCode}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <br>
    <br>

    <div class="container level-item">
        <table class="table is-bordered">
            <thead>
            <tr>
                <th><fmt:message key="subjects" bundle="${viewProfileEntrantBundle}"/></th>
                <th><fmt:message key="results" bundle="${viewProfileEntrantBundle}"/></th>
                <th><fmt:message key="sum" bundle="${viewProfileEntrantBundle}"/></th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td>
                    <fmt:message key="certificate" bundle="${viewProfileEntrantBundle}"/> <br>
                    <c:forEach var="subjectResult" items="${entrant.results}">
                        <fmt:message key="${subjectNames.get(subjectResult.subjectId.toString())}"
                                     bundle="${subjectsBundle}"/> <br>
                    </c:forEach>
                </td>
                <td>
                    ${entrant.certificate}<br>
                    <c:forEach var="subjectResult" items="${entrant.results}">
                        ${subjectResult.points}<br>
                    </c:forEach>
                </td>
                <td>
                    <c:set var="sum" value="${0}"/>
                    <c:forEach var="subjectResult" items="${entrant.results}">
                        <c:set var="sum" value="${sum + subjectResult.points}"/>
                    </c:forEach>
                    ${sum+entrant.certificate}
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <br>
    <br>
    <div class="container level-item">
        <table class="table is-bordered">
            <thead>
            <tr>
                <th><fmt:message key="priority" bundle="${viewProfileEntrantBundle}"/></th>
                <th><fmt:message key="faculty" bundle="${viewProfileEntrantBundle}"/></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="priorityFaculty" items="${entrant.priorities}">
                <tr>
                    <td>
                            ${priorityFaculty.priority}
                    </td>
                    <td>
                        <fmt:message key="${facultyNames.get(priorityFaculty.facultyId.toString())}"
                                     bundle="${facultiesBundle}"/> <br>
                    </td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
    </div>

    <section class="section">
        <div class="container level-item">
            <div class="columns is-multiline">
                <c:forEach var="photo" items="${entrant.user.photos}">
                    <div class="column is-3">
                        <figure class="image">
                            <img src="${pageContext.request.contextPath}/images/${photo}" alt="photo">
                        </figure>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

</form>

</body>
</html>
