<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.updateCertificates" var="updateCertificatesBundle"/>
<fmt:setBundle basename="prop.internalization.jsp.subjects" var="subjectsBundle"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${updateCertificatesBundle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/headerStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProject.css">
    <link rel="script" href="${pageContext.request.contextPath}/js/script.js">
</head>
<body>
<%@ include file="header.jsp" %>
<div id="login-page" class="container">
    <h1 class="title has-text-centered"><fmt:message key="label.updating"
                                                     bundle="${updateCertificatesBundle}"/></h1>

    <form name="updateCertificatesForm" method="post" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="update_certificates_post">

        <div class="field is-horizontal">
            <div class="field-label is-normal">
                <label class="label"> <fmt:message key="certificate" bundle="${updateCertificatesBundle}"/></label>
            </div>
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <input class="input" type="text"
                               placeholder="<fmt:message key="certificate"  bundle="${updateCertificatesBundle}"/>"
                               name="certificate" required
                               pattern="^[1-9][0-9]?$|^100$"
                               title="<fmt:message key="number.title"  bundle="${updateCertificatesBundle}"/>"
                               value="${certificateValue}"
                        >
                        <c:if test="${errors.certificateError!=null}"> <fmt:message key="${errors.certificateError}"
                                                                                    bundle="${updateCertificatesBundle}"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <c:forEach var="subject" items="${subjects}">
            <div class="field is-horizontal">
                <div class="field-label is-normal">
                    <label class="label"><fmt:message key="${subject.name}"
                                                      bundle="${subjectsBundle}"/></label>
                </div>
                <div class="field-body">
                    <div class="field">
                        <div class="control">
                            <input class="input" type="text"
                                   placeholder="<fmt:message key="${subject.name}"  bundle="${subjectsBundle}"/>"
                                   name="subject_${subject.id}" required title="${number.title}"
                                   pattern="^[1-9][0-9]?$|^100$" value="${points.get(subject.getId())}"
                                   title="<fmt:message key="number.title"  bundle="${updateCertificatesBundle}"/>"
                            >
                            <c:if test="${errors.certificateError!=null}"> <fmt:message
                                    key="${errors.certificateError}"
                                    bundle="${updateCertificatesBundle}"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <div class="field is-horizontal">
            <div class="field-body">
                <div class="field">
                    <div class="control">
                        <c:if test="${errors.subjectsError!=null}"><p><fmt:message key="${errors.subjectsError}"
                                                                                   bundle="${updateCertificatesBundle}"/></p>
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
                            <fmt:message key="update" bundle="${updateCertificatesBundle}"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
