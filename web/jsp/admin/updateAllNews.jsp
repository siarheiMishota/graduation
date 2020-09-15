<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.updateAllNews" var="updateAllNewsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
    <title><fmt:message key="title" bundle="${updateAllNewsBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>
<h1 class="title has-text-centered"><fmt:message key="label.update.all.news" bundle="${updateAllNewsBundle}"/></h1>

<div class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${updateAllNewsBundle}"/></h3>
    </c:if>

    <section class="section">
        <div class="container level-item">
            <br>
            <table class="table is-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="news.name.ru" bundle="${updateAllNewsBundle}"/></th>
                    <th><fmt:message key="news.name.en" bundle="${updateAllNewsBundle}"/></th>
                    <th><fmt:message key="brief.description.ru" bundle="${updateAllNewsBundle}"/></th>
                    <th><fmt:message key="brief.description.en" bundle="${updateAllNewsBundle}"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="news" items="${allNews}">
                    <tr>
                        <td class="lineBreak">
                            <a href="${pageContext.request.contextPath}/controller?command=admin_update_news_get&newsId=${news.id}">
                                    ${news.nameEn}
                            </a>
                        </td>
                        <td class="lineBreak">
                            <a href="${pageContext.request.contextPath}/controller?command=admin_update_news_get&newsId=${news.id}">
                                    ${news.nameRu}
                            </a>
                        </td>
                        <td class="lineBreak">
                                ${news.briefDescriptionEn}
                        </td>
                        <td class="lineBreak">
                                ${news.briefDescriptionRu}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <br>

        <div class="container level-item">
            <nav class="pagination" role="navigation" aria-label="pagination">
                <c:if test="${numberOfPage>1}">
                    <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage-1}"
                       class="pagination-previous"><fmt:message key="previous" bundle="${updateAllNewsBundle}"/>
                    </a>
                </c:if>
                <c:if test="${numberOfPage<numberOfPages}">

                    <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage+1}"
                       class="pagination-next">
                        <fmt:message key="next" bundle="${updateAllNewsBundle}"/>
                    </a>
                </c:if>

                <ul class="pagination-list">
                    <li>
                        <c:if test="${numberOfPage==1}">
                            <a href="/controller?command=admin_update_all_news_get&page=1"
                               class="pagination-link is-current"
                               aria-label="Goto page 1">1
                            </a>
                        </c:if>

                        <c:if test="${numberOfPage!=1}">
                            <a href="/controller?command=admin_update_all_news_get&page=1" class="pagination-link"
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
                            <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage-1}"
                               class="pagination-link"
                               aria-label="Goto
                                    page ${numberOfPage-1}">${numberOfPage-1}
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${numberOfPage>1}">

                        <li>
                            <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage}"
                               class="pagination-link is-current" aria-label="Goto page ${numberOfPage}">${numberOfPage}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPages>1&&numberOfPage<numberOfPages}">
                        <li>
                            <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage+1}"
                               class="pagination-link" aria-label="Goto page ${numberOfPage+1}">${numberOfPage+1}
                            </a>
                        </li>
                    </c:if>

                    <c:if test="${numberOfPage==1&&numberOfPages>2}">
                        <li>
                            <a href="/controller?command=admin_update_all_news_get&page=${numberOfPage+2}"
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
                                <a href="/controller?command=admin_update_all_news_get&page=${numberOfPages}"
                                   class="pagination-link" aria-label="Goto page ${numberOfPages}">${numberOfPages}
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                </ul>
            </nav>
        </div>

    </section>
</div>
</body>
</html>
