<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.viewFaculties" var="viewFacultiesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${viewFacultiesBundle}"/></title>

</head>
<body>
<%@ include file="header.jsp" %>

    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${viewFacultiesBundle}"/></h3>
    </c:if>

    <input type="hidden" name="command" value="main_post">

    <section class="section">
        <div class="container level-item">
            <br>
            <table class="table is-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="name.faculty" bundle="${viewFacultiesBundle}"/></th>
                    <th><fmt:message key="need.subjects" bundle="${viewFacultiesBundle}"/></th>
                    <th><fmt:message key="number.free.places" bundle="${viewFacultiesBundle}"/></th>
                    <th><fmt:message key="number.pay.places" bundle="${viewFacultiesBundle}"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="faculty" items="${faculties}">
                    <tr>
                        <td>
                            <fmt:message key="${faculty.name}" bundle="${facultiesBundle}"/>
                        </td>
                        <td>
                            <c:forEach var="subject" items="${subjects.get(faculty.getId())}">
                                <fmt:message key="${subject}" bundle="${subjectsBundle}"/> <br>
                            </c:forEach>
                        </td>
                        <td>${faculty.numberFreePlaces}</td>
                        <td>${faculty.numberPayPlaces}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="container level-item">
            <nav class="pagination" role="navigation" aria-label="pagination">
                <c:if test="${numberOfPage>1}">
                    <a href="/controller?command=view_faculties_get&page=${numberOfPage-1}"
                       class="pagination-previous"><fmt:message key="previous" bundle="${viewFacultiesBundle}"/>
                    </a>
                </c:if>
                <c:if test="${numberOfPage<numberOfPages}">

                    <a href="/controller?command=view_faculties_get&page=${numberOfPage+1}" class="pagination-next">
                        <fmt:message key="next" bundle="${viewFacultiesBundle}"/>
                    </a>
                </c:if>

                <ul class="pagination-list">
                    <li>
                        <c:if test="${numberOfPage==1}">
                            <a href="/controller?command=view_faculties_get&page=1" class="pagination-link is-current"
                               aria-label="Goto page 1">1
                            </a>
                        </c:if>

                        <c:if test="${numberOfPage!=1}">
                            <a href="/controller?command=view_faculties_get&page=1" class="pagination-link"
                               aria-label="Goto page 1">1
                            </a>
                        </c:if>
                    </li>

                    <c:if test="${numberOfPages>=5&&numberOfPage>3}">
                        <li>
                            <span class="pagination-ellipsis">&hellip;</span>
                        </li>
                    </c:if>


                    <c:if test="${numberOfPage>2}">
                        <li>
                            <a href="/controller?command=view_faculties_get&page=${numberOfPage-1}"
                               class="pagination-link"
                               aria-label="Goto
                            page ${numberOfPage-1}">${numberOfPage-1}
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${numberOfPage>1}">

                        <li>
                            <a href="/controller?command=view_faculties_get&page=${numberOfPage}"
                               class="pagination-link is-current" aria-label="Goto page ${numberOfPage}">${numberOfPage}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPages>1&&numberOfPage<numberOfPages}">
                        <li>
                            <a href="/controller?command=view_faculties_get&page=${numberOfPage+1}"
                               class="pagination-link" aria-label="Goto page ${numberOfPage+1}">${numberOfPage+1}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPage==1&&numberOfPages>2}">
                        <li>
                            <a href="/controller?command=view_faculties_get&page=${numberOfPage+2}"
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
                                <a href="/controller?command=view_faculties_get&page=${numberOfPages}"
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
