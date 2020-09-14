<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.admin.addNews" var="addNewsBundle"/>

<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.07af92a5.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">

    <title><fmt:message key="title" bundle="${addNewsBundle}"/></title>

</head>
<body>
<%@ include file="../header.jsp" %>
<h1 class="title has-text-centered"><fmt:message key="label.adding.news" bundle="${addNewsBundle}"/></h1>

<div id="login-page" class="container">
    <form name="addNewsForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="admin_add_news_post">

        <c:if test="${message!=null}">
            <h3 class="title has-text-centered"><fmt:message key="${message}" bundle="${addNewsBundle}"/></h3>
        </c:if>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="news.name.ru" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input"
                               type="text"
                               placeholder="<fmt:message key="news.name.ru" bundle="${addNewsBundle}"/>"
                               title=" <fmt:message key="long.name.ru" bundle="${addNewsBundle}"/>"
                               name="nameRu"
                               value="${nameRu}"
                               required>
                        <c:if test="${errors.nameRu!=null}"> <fmt:message key="${errors.nameRu}.name.ru"
                                                                          bundle="${addNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="news.name.en" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input"
                               type="text"
                               placeholder="<fmt:message key="news.name.en" bundle="${addNewsBundle}"/>"
                               title="<fmt:message key="long.name.en" bundle="${addNewsBundle}"/>"
                               name="nameEn"
                               value="${nameEn}"
                               required>
                        <c:if test="${errors.nameEn!=null}"> <fmt:message key="${errors.nameEn}.name.en"
                                                                          bundle="${addNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="brief.description.ru" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  placeholder="<fmt:message key="brief.description.ru" bundle="${addNewsBundle}"/>"
                                  title="<fmt:message key="long.brief.description.ru" bundle="${addNewsBundle}"/>"
                                  name="briefDescriptionRu"
                                  required
                                  rows="3"
                                  cols="20">${briefDescriptionRu}</textarea>
                        <c:if test="${errors.briefDescriptionRu!=null}">
                            <fmt:message key="${errors.briefDescriptionRu}.brief.description.ru"
                                         bundle="${addNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="brief.description.en" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="briefDescriptionEn"
                                  title="<fmt:message key="long.brief.description.en" bundle="${addNewsBundle}"/>"
                                  placeholder="<fmt:message key="brief.description.en" bundle="${addNewsBundle}"/>"
                                  required
                                  rows="3"
                                  cols="20">${briefDescriptionEn}</textarea>
                        <c:if test="${errors.briefDescriptionEn!=null}"> <fmt:message
                                key="${errors.briefDescriptionEn}.brief.description.en"
                                bundle="${addNewsBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="russian.variable" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="russianVariable" required
                                  placeholder="<fmt:message key="russian.variable" bundle="${addNewsBundle}"/>"
                                  title="<fmt:message key="long.russian.variable" bundle="${addNewsBundle}"/>"
                                  rows="6"
                                  cols="20">${russianVariable}</textarea>
                    </div>
                </div>
                <c:if test="${errors.russianVariable!=null}"> <fmt:message
                        key="${errors.russianVariable}.russian.variable"
                        bundle="${addNewsBundle}"/>
                </c:if>
            </div>
        </div>
        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="english.variable" bundle="${addNewsBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <textarea class="textarea is-large"
                                  name="englishVariable"
                                  placeholder="<fmt:message key="english.variable" bundle="${addNewsBundle}"/>"
                                  title="<fmt:message key="long.english.variable" bundle="${addNewsBundle}"/>"
                                  rows="6"
                                  cols="20">${englishVariable}</textarea>
                    </div>
                </div>
                <c:if test="${errors.englishVariable!=null}"> <fmt:message
                        key="${errors.englishVariable}.english.variable"
                        bundle="${addNewsBundle}"/>
                </c:if>
            </div>
        </div>

        <div class="field is-horizontal">
            <button class="button is-primary is-fullwidth">
                <fmt:message key="add.news" bundle="${addNewsBundle}"/>
            </button>
        </div>
    </form>
</div>
</body>
</html>
