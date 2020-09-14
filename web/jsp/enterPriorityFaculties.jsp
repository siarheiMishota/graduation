<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.enterPriorityFaculties" var="enterPriorityFacultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${enterPriorityFacultiesBundle}"/></title>
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
                                                         bundle="${enterPriorityFacultiesBundle}"/></h3>
    </c:if>

    <h1 class="title has-text-centered"><fmt:message key="label.priority.facylties"
                                                     bundle="${enterPriorityFacultiesBundle}"/></h1>

    <form name="enterPriorityFacultiesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="enter_priority_faculties_post">
        <input type="hidden" name="priority" value="${priority}">

        <div class="field is-horizontal">
            <div class="field-label  is-normal">
                <label class="label">${priority}</label>
            </div>
            <c:if test="${errors.priority!=null}"> <fmt:message
                    key="${errors.priority}"
                    bundle="${enterPriorityFacultiesBundle}"/>
            </c:if>
            <div class="select is-primary ">
                <div class="select is-rounded is-fullwidth">
                    <select name="faculty">
                        <c:forEach var="faculty" items="${faculties}">
                            <option value="${faculty.id}"><fmt:message key="${faculty.name}"
                                                                       bundle="${facultiesBundle}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <c:if test="${errors.faculty!=null}"> <fmt:message
                    key="${errors.faculty}"
                    bundle="${enterPriorityFacultiesBundle}"/>
            </c:if>
        </div>

        <div class="field is-horizontal">
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <c:if test="${errors.facultiesError!=null}"><p><fmt:message key="${errors.facuFltiesError}"
                                                                                    bundle="${priorityacultiesBundle}"/></p>
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
                            <fmt:message key="save" bundle="${enterPriorityFacultiesBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
