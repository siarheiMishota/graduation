<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewPriorityFaculties" var="viewPriorityFacultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${viewPriorityFacultiesBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="view.priority.faculties"
                                                     bundle="${viewPriorityFacultiesBundle}"/></h1>
    <section class="section">
        <div class="container level-item">
            <br>
            <table class="table is-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="priority" bundle="${viewPriorityFacultiesBundle}"/></th>
                    <th><fmt:message key="faculty" bundle="${viewPriorityFacultiesBundle}"/></th>
                </tr>
                </thead>

                <tbody>

                <c:if test="${!faculties.isEmpty()&&!priorities.isEmpty()}">
                    <c:forEach var="priorityFaculty" items="${faculties.entrySet()}">
                        <tr>
                            <td>
                                ${priorityFaculty.getKey()}
                            </td>
                            <td>
                                <fmt:message key="${priorityFaculty.getValue()}" bundle="${facultiesBundle}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>

    </section>
</div>

</body>
</html>
