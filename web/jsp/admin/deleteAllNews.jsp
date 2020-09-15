<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.deleteAllNews" var="deleteAllNewsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${deleteAllNewsBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <c:if test="${message!=null}">
        <h3 class="title has-text-centered"><fmt:message key="${message}"
                                                         bundle="${deleteAllNewsBundle}"/></h3>
    </c:if>
    <h1 class="title has-text-centered"><fmt:message key="label.delete.all.news"
                                                     bundle="${deleteAllNewsBundle}"/></h1>
    <form name="deleteAllNewsForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_delete_all_news_post">
        <section class="section">
            <div class="container level-item">
                <br>
                <table class="table is-bordered">
                    <thead>
                    <tr>
                        <th><fmt:message key="choice" bundle="${deleteAllNewsBundle}"/></th>
                        <th><fmt:message key="news.name.ru" bundle="${deleteAllNewsBundle}"/></th>
                        <th><fmt:message key="news.name.en" bundle="${deleteAllNewsBundle}"/></th>
                        <th><fmt:message key="brief.description.ru" bundle="${deleteAllNewsBundle}"/></th>
                        <th><fmt:message key="brief.description.en" bundle="${deleteAllNewsBundle}"/></th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:if test="${!allNews.isEmpty()}">
                        <c:forEach var="news" items="${allNews}">
                            <tr>
                                <td>
                                    <input type="checkbox" name="news"
                                           value="${news.id}">
                                </td>
                                <td class="lineBreak" >
                                        ${news.nameRu}
                                </td>
                                <td class="lineBreak">
                                        ${news.nameEn}
                                </td>
                                <td class="lineBreak">
                                        ${fn:substring(news.briefDescriptionRu,0,200)}
                                </td>
                                <td class="lineBreak">
                                        ${fn:substring(news.briefDescriptionEn,0,200)}
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
                            <fmt:message key="delete" bundle="${deleteAllNewsBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <div class="container level-item">
        <nav class="pagination" role="navigation" aria-label="pagination">
            <c:if test="${numberOfPage>1}">
                <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage-1}"
                   class="pagination-previous"><fmt:message key="previous" bundle="${deleteAllNewsBundle}"/>
                </a>
            </c:if>
            <c:if test="${numberOfPage<numberOfPages}">

                <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage+1}"
                   class="pagination-next">
                    <fmt:message key="next" bundle="${deleteAllNewsBundle}"/>
                </a>
            </c:if>

            <ul class="pagination-list">
                <li>
                    <c:if test="${numberOfPage==1}">
                        <a href="/controller?command=admin_delete_all_news_get&page=1"
                           class="pagination-link is-current"
                           aria-label="Goto page 1">1
                        </a>
                    </c:if>

                    <c:if test="${numberOfPage!=1}">
                        <a href="/controller?command=admin_delete_all_news_get&page=1" class="pagination-link"
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
                        <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage-1}"
                           class="pagination-link"
                           aria-label="Goto
                                    page ${numberOfPage-1}">${numberOfPage-1}
                        </a>
                    </li>
                </c:if>
                <c:if test="${numberOfPage>1}">

                    <li>
                        <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage}"
                           class="pagination-link is-current" aria-label="Goto page ${numberOfPage}">${numberOfPage}
                        </a>
                    </li>
                </c:if>

                <c:if test="${numberOfPages>1&&numberOfPage<numberOfPages}">
                    <li>
                        <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage+1}"
                           class="pagination-link" aria-label="Goto page ${numberOfPage+1}">${numberOfPage+1}
                        </a>
                    </li>
                </c:if>

                <c:if test="${numberOfPage==1&&numberOfPages>2}">
                    <li>
                        <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPage+2}"
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
                            <a href="/controller?command=admin_delete_all_news_get&page=${numberOfPages}"
                               class="pagination-link" aria-label="Goto page ${numberOfPages}">${numberOfPages}
                            </a>
                        </li>
                    </c:if>
                </c:if>
            </ul>
        </nav>
    </div>

</div>

</body>
</html>
