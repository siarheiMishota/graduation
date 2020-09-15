<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.main" var="mainBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.faculties" var="facultiesBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${mainBundle}"/></title>

</head>
<body>
<%@ include file="header.jsp" %>

<div class="container">
    <form name="mainForm" method="post" action="${pageContext.request.contextPath}/controller">
        <c:if test="${message!=null}">
            <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${mainBundle}"/></h3>
        </c:if>

        <input type="hidden" name="command" value="main_post">

        <section class="section">
            <c:forEach var="news" items="${allNews}">

                <h5 class="title has-text-centered">
                    <a href="/controller?command=view_news_get&newsId=${news.id}"
                       aria-label="Go to news">
                        <c:if test="${language.equals('en')}">${news.nameEn}</c:if>
                        <c:if test="${language.equals('ru')}">${news.nameRu}</c:if>
                    </a>
                </h5>

                <div class="field is-horizontal ">
                    <div class="field-body">
                        <div class="field">
                            <div class="control">
                            <textarea readonly class="textarea" rows="3" cols="15"><c:if
                                    test="${language.equals('en')}">${news.briefDescriptionEn}</c:if><c:if
                                    test="${language.equals('ru')}">${news.briefDescriptionRu}</c:if></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
            </c:forEach>

            <div class="container level-item">
                <nav class="pagination" role="navigation" aria-label="pagination">
                    <c:if test="${numberOfPage>1}">
                        <a href="/controller?command=main_get&page=${numberOfPage-1}"
                           class="pagination-previous"><fmt:message key="previous" bundle="${mainBundle}"/>
                        </a>
                    </c:if>
                    <c:if test="${numberOfPage<numberOfPages}">

                        <a href="/controller?command=main_get&page=${numberOfPage+1}" class="pagination-next">
                            <fmt:message key="next" bundle="${mainBundle}"/>
                        </a>
                    </c:if>

                    <ul class="pagination-list">
                        <li>
                            <c:if test="${numberOfPage==1}">
                                <a href="/controller?command=main_get&page=1"
                                   class="pagination-link is-current"
                                   aria-label="Goto page 1">1
                                </a>
                            </c:if>

                            <c:if test="${numberOfPage!=1}">
                                <a href="/controller?command=main_get&page=1" class="pagination-link"
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
                                <a href="/controller?command=main_get&page=${numberOfPage-1}"
                                   class="pagination-link"
                                   aria-label="Goto
                            page ${numberOfPage-1}">${numberOfPage-1}
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${numberOfPage>1}">

                            <li>
                                <a href="/controller?command=main_get&page=${numberOfPage}"
                                   class="pagination-link is-current"
                                   aria-label="Goto page ${numberOfPage}">${numberOfPage}
                                </a>
                            </li>
                        </c:if>

                        <c:if test="${numberOfPages>1&&numberOfPage<numberOfPages}">
                            <li>
                                <a href="/controller?command=main_get&page=${numberOfPage+1}"
                                   class="pagination-link" aria-label="Goto page ${numberOfPage+1}">${numberOfPage+1}
                                </a>
                            </li>
                        </c:if>

                        <c:if test="${numberOfPage==1&&numberOfPages>2}">
                            <li>
                                <a href="/controller?command=main_get&page=${numberOfPage+2}"
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
                                    <a href="/controller?command=main_get&page=${numberOfPages}"
                                       class="pagination-link" aria-label="Goto page ${numberOfPages}">${numberOfPages}
                                    </a>
                                </li>
                            </c:if>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </section>
    </form>
</div>

</body>
</html>
