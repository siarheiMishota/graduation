<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.deleteCertificates" var="deleteCertificatesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${deleteCertificatesBundle}"/></title>
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
                                                         bundle="${deleteCertificatesBundle}"/></h3>
    </c:if>
    <h1 class="title has-text-centered"><fmt:message key="label.delete.certificates"
                                                     bundle="${deleteCertificatesBundle}"/></h1>
    <form name="deleteCertificatesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="delete_certificates_post">
        <section class="section">
            <div class="container level-item">
                <br>
                <table class="table is-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="choice" bundle="${deleteCertificatesBundle}"/></th>
                        <th><fmt:message key="subject" bundle="${deleteCertificatesBundle}"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:if test="${!subjects.isEmpty()}">
                        <c:forEach var="subject" items="${subjects}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="subject"
                                           value="${subject.id}">
                                </td>
                                <td>
                                    <fmt:message key="${subject.name}" bundle="${subjectsBundle}"/>
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
                            <fmt:message key="delete" bundle="${deleteCertificatesBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${entrant!=null&&!entrant.priorities.isEmpty()}">
        <h3 class="title has-text-centered"><fmt:message key="warning"
                                                         bundle="${deleteCertificatesBundle}"/></h3>
    </c:if>
</div>

</body>
</html>
