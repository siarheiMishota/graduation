<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewCertificates" var="viewCertificatesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${viewCertificatesBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="view.certificates"
                                                     bundle="${viewCertificatesBundle}"/></h1>
    <section class="section">
        <div class="container level-item">
            <br>
            <table class="table is-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="name" bundle="${viewCertificatesBundle}"/></th>
                    <th><fmt:message key="points" bundle="${viewCertificatesBundle}"/></th>
                </tr>
                </thead>

                <tbody>
                <tr>
                    <td>
                        <fmt:message key="certificate" bundle="${viewCertificatesBundle}"/>

                    </td>
                    <td>
                        ${certificate}
                    </td>
                </tr>

                <c:if test="${!subjectNames.isEmpty()&&!points.isEmpty()}">
                    <c:forEach var="index" begin="0" end="${subjectNames.size()-1}">
                        <tr>
                            <td>
                                <fmt:message key="${subjectNames.get(index)}" bundle="${subjectsBundle}"/>
                            </td>
                            <td>
                                    ${points.get(index)}
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
