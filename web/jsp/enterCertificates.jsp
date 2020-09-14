<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.enterCertificates" var="enterCertificatesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${enterCertificatesBundle}"/></title>
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
                                                         bundle="${enterCertificatesBundle}"/></h3>
    </c:if>

    <h1 class="title has-text-centered"><fmt:message key="label.enter.certificates"
                                                     bundle="${enterCertificatesBundle}"/></h1>

    <form name="enterCertificatesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="enter_certificates_post">

        <c:if test="${entrant==null}">
            <div class="field is-horizontal">
                <div class="field-label is-normal">
                    <label class="label"> <fmt:message key="certificate" bundle="${enterCertificatesBundle}"/></label>
                </div>
                <div class="field-body">
                    <div class="field">
                        <div class="control">
                            <input class="input" type="text"
                                   placeholder="<fmt:message key="marks"  bundle="${enterCertificatesBundle}"/>"
                                   name="certificate" required
                                   pattern="^[1-9][0-9]?$|^100$"
                                   title="<fmt:message key="number.title"  bundle="${enterCertificatesBundle}"/>"
                                   value="<c:if test="${entrant!=null}"> ${certificateValue}    </c:if>"
                            >
                            <c:if test="${errors.mark!=null}"> <fmt:message key="${errors.mark}"
                                                                            bundle="${enterCertificatesBundle}"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="field is-horizontal">

            <div class="select is-primary ">
                <div class="select is-rounded is-fullwidth  ">
                    <select name="subject">
                        <c:forEach var="faculty" items="${subjects}">
                            <option value="${faculty.id}"><fmt:message key="${faculty.name}"
                                                                       bundle="${subjectsBundle}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <c:if test="${errors.subject!=null}"> <fmt:message
                    key="${errors.subject}"
                    bundle="${enterCertificatesBundle}"/>
            </c:if>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text"
                               placeholder="<fmt:message key="marks"  bundle="${enterCertificatesBundle}"/>"
                               name="mark" required
                               pattern="^[1-9][0-9]?$|^100$"
                               title="<fmt:message key="number.title"  bundle="${enterCertificatesBundle}"/>"
                        >
                        <c:if test="${errors.mark!=null}"> <fmt:message
                                key="${errors.mark}"
                                bundle="${enterCertificatesBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="field is-horizontal">
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <c:if test="${errors.subjectsError!=null}"><p><fmt:message key="${errors.subjectsError}"
                                                                                   bundle="${enterCertificatesBundle}"/></p>
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
                            <fmt:message key="save" bundle="${enterCertificatesBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${entrant!=null&&!entrant.priorities.isEmpty()}">
        <h3 class="title has-text-centered"><fmt:message key="warning"
                                                         bundle="${enterCertificatesBundle}"/></h3>
    </c:if>
</div>

</body>
</html>
