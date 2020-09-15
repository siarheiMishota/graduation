<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.priorityFaculties" var="enterPriorityFacultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${enterPriorityFacultiesBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Instyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.priority.facylties"
                                                     bundle="${enterPriorityFacultiesBundle}"/></h1>

    <form name="priorityFacultiesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="enter_priority_faculties_post">

        <c:forEach begin="1" end="${numberPriorityRows}" var="index">

            <div class="field is-horizontal">
                <div class="field-label  is-normal">
                    <label class="label">${index}</label>
                </div>

                <div class="select is-primary ">
                    <div class="select is-rounded is-fullwidth  ">
                        <select name="priority_${index}">
                            <c:forEach var="faculty" items="${faculties}">
                                <option value="${faculty.id}"><fmt:message key="${faculty.name}"
                                                                           bundle="${facultiesBundle}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </c:forEach>

        <div class="field is-horizontal">
            <button class="button is-primary is-fullwidth">
                <fmt:message key="save" bundle="${enterPriorityFacultiesBundle}"/>
            </button>
        </div>
    </form>
</div>


</body>
</html>
