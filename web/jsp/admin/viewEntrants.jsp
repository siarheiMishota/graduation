<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.viewEntrants" var="viewEntrantsBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${viewEntrantsBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>

    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${viewEntrantsBundle}"/></h3>
    </c:if>


    <section class="section">
        <div class="container level-item">
            <br>
            <table class="table is-bordered">
                <thead>
                    <th><fmt:message key="fullName" bundle="${viewEntrantsBundle}"/></th>
                    <th><fmt:message key="subjects" bundle="${viewEntrantsBundle}"/></th>
                    <th><fmt:message key="results" bundle="${viewEntrantsBundle}"/></th>
                    <th><fmt:message key="sum" bundle="${viewEntrantsBundle}"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="entrant" items="${entrants}">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/controller?command=admin_view_profile_entrant_get&entrantId=${entrant.id}">
                                    ${entrant.user.surname}<br>
                                    ${entrant.user.firstName}<br>
                                    ${entrant.user.fatherName}<br>
                            </a>
                        </td>
                        <td>
                            <fmt:message key="certificate" bundle="${viewEntrantsBundle}"/> <br>
                            <c:forEach var="subjectResult" items="${entrant.results}">
                                <fmt:message key="${subjectNames[subjectResult.subjectId.toString()]}"
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
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="container level-item">
            <nav class="pagination" role="navigation" aria-label="pagination">
                <c:if test="${numberOfPage>1}">
                    <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage-1}"
                       class="pagination-previous"><fmt:message key="previous" bundle="${viewEntrantsBundle}"/>
                    </a>
                </c:if>
                <c:if test="${numberOfPage<numberOfPages}">

                    <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage+1}"
                       class="pagination-next">
                        <fmt:message key="next" bundle="${viewEntrantsBundle}"/>
                    </a>
                </c:if>

                <ul class="pagination-list">
                    <li>
                        <c:if test="${numberOfPage==1}">
                            <a href="/controller?command=admin_view_entrants_get&page=1"
                               class="pagination-link is-current"
                               aria-label="Goto page 1">1
                            </a>
                        </c:if>

                        <c:if test="${numberOfPage!=1}">
                            <a href="/controller?command=admin_view_entrants_get&page=1" class="pagination-link"
                               aria-label="Goto page 1">1
                            </a>
                        </c:if>
                    </li>

                    <c:if test="${numberOfPages>5&&numberOfPage>3}">
                        <li>
                            <span class="pagination-ellipsis">&hellip;</span>
                        </li>
                    </c:if>


                    <c:if test="${numberOfPage>2}">
                        <li>
                            <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage-1}"
                               class="pagination-link"
                               aria-label="Goto
                                    page ${numberOfPage-1}">${numberOfPage-1}
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${numberOfPage>1}">

                        <li>
                            <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage}"
                               class="pagination-link is-current" aria-label="Goto page ${numberOfPage}">${numberOfPage}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPages>1&&numberOfPage<numberOfPages}">
                        <li>
                            <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage+1}"
                               class="pagination-link" aria-label="Goto page ${numberOfPage+1}">${numberOfPage+1}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPage==1&&numberOfPages>2}">
                        <li>
                            <a href="/controller?command=admin_view_entrants_get&page=${numberOfPage+2}"
                               class="pagination-link" aria-label="Goto page ${numberOfPage+2}">${numberOfPage+2}
                            </a>
                        </li>
                    </c:if>


                    <c:if test="${numberOfPages>=5}">
                        <c:if test="${numberOfPages-2>numberOfPage}">
                            <li>
                                <span class="pagination-ellipsis">&hellip;</span>
                            </li>
                        </c:if>
                        <c:if test="${numberOfPages-1>numberOfPage}">

                            <li>
                                <a href="/controller?command=admin_view_entrants_get&page=${numberOfPages}"
                                   class="pagination-link" aria-label="Goto page ${numberOfPages}">${numberOfPages}
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
            </nav>
        </div>
    </section>
</body>
</html>
