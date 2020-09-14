<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.deletePriorityFaculties" var="deletePriorityFacultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${deletePriorityFacultiesBundle}"/></title>
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
                                                         bundle="${deletePriorityFacultiesBundle}"/></h3>
    </c:if>
    <h1 class="title has-text-centered"><fmt:message key="label.delete.priority.faculties"
                                                     bundle="${deletePriorityFacultiesBundle}"/></h1>
    <form name="deletePriorityFacultiesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="delete_priority_faculties_post">
        <section class="section">
            <div class="container level-item">
                <br>
                <table class="table is-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="priority" bundle="${deletePriorityFacultiesBundle}"/></th>
                        <th><fmt:message key="faculty" bundle="${deletePriorityFacultiesBundle}"/></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:if test="${!faculties.isEmpty()&&!priorities.isEmpty()}">
                        <c:forEach var="priorityFaculty" items="${faculties.entrySet()}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="priority"
                                           value="${priorityFaculty.getKey()}">${priorityFaculty.getKey()}
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

        <div class="field is-horizontal">
            <div class="field-label">
            </div>
            <div class="field-body">
                <div class="field is-grouped">
                    <div class="control is-expanded">
                        <button class="button is-primary is-fullwidth">
                            <fmt:message key="delete" bundle="${deletePriorityFacultiesBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
