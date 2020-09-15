<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.updateNews" var="updateNewsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${updateNewsBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>
<h1 class="title has-text-centered"><fmt:message key="label.update.news" bundle="${updateNewsBundle}"/></h1>

<div id="login-page" class="container">
    <form name="mainForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_update_news_post">
        <input type="hidden" name="newsId" value="${news.id}">
        <c:if test="${message!=null}">
            <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${updateNewsBundle}"/></h3>
        </c:if>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="news.name.ru" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input"
                               type="text"
                               placeholder="<fmt:message key="news.name.ru" bundle="${updateNewsBundle}"/>"
                               title=" <fmt:message key="long.name.ru" bundle="${updateNewsBundle}"/>"
                               name="nameRu"
                               value="${news.nameRu}"
                               required>
                        <c:if test="${errors.nameRu!=null}"> <fmt:message key="${errors.nameRu}.name.ru"
                                                                          bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="news.name.en" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input"
                               type="text"
                               placeholder="<fmt:message key="news.name.en" bundle="${updateNewsBundle}"/>"
                               title="<fmt:message key="long.name.en" bundle="${updateNewsBundle}"/>"
                               name="nameEn"
                               value="${news.nameEn}"
                               required>
                        <c:if test="${errors.nameEn!=null}"> <fmt:message key="${errors.nameEn}.name.en"
                                                                          bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="brief.description.ru" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  placeholder="<fmt:message key="brief.description.ru" bundle="${updateNewsBundle}"/>"
                                  title="<fmt:message key="long.brief.description.ru" bundle="${updateNewsBundle}"/>"
                                  name="briefDescriptionRu"
                                  required
                                  rows="3"
                                  cols="20">${news.briefDescriptionRu}</textarea>
                        <c:if test="${errors.briefDescriptionRu!=null}">
                            <fmt:message key="${errors.briefDescriptionRu}.brief.description.ru"
                                         bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="brief.description.en" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="briefDescriptionEn"
                                  title="<fmt:message key="long.brief.description.en" bundle="${updateNewsBundle}"/>"
                                  placeholder="<fmt:message key="brief.description.en" bundle="${updateNewsBundle}"/>"
                                  required
                                  rows="3"
                                  cols="20">${news.briefDescriptionEn}</textarea>
                        <c:if test="${errors.briefDescriptionEn!=null}"> <fmt:message
                                key="${errors.briefDescriptionEn}.brief.description.en"
                                bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="russian.variable" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="russianVariable" required
                                  placeholder="<fmt:message key="russian.variable" bundle="${updateNewsBundle}"/>"
                                  title="<fmt:message key="long.russian.variable" bundle="${updateNewsBundle}"/>"
                                  rows="6"
                                  cols="20">${news.russianVariable}</textarea>
                        <c:if test="${errors.russianVariable!=null}"> <fmt:message
                                key="${errors.russianVariable}.russian.variable"
                                bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="english.variable" bundle="${updateNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="englishVariable"
                                  placeholder="<fmt:message key="english.variable" bundle="${updateNewsBundle}"/>"
                                  title="<fmt:message key="long.english.variable" bundle="${updateNewsBundle}"/>"
                                  rows="6"
                                  cols="20">${news.englishVariable}</textarea>
                        <c:if test="${errors.englishVariable!=null}"> <fmt:message
                                key="${errors.englishVariable}.english.variable"
                                bundle="${updateNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <button class="button is-primary is-fullwidth">
                <fmt:message key="update" bundle="${updateNewsBundle}"/>
            </button>
        </div>
    </form>
</div>
</body>
</html>
