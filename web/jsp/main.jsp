<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="prop.internalization.jsp.main" var="rb"/>

<html>
<head>
    <title><fmt:message key="graduation" bundle="${rb}"/></title>
</head>
<body>

<form name="mainForm" method="post" action="${pageContext.request.contextPath}/controller">

    <input type="hidden" name="command" value="main">

    <c:forEach var="faculty" items="${faculties}">
        <p><fmt:message key="faculty" bundle="${rb}"/> ${faculty.name}</p>
        <p><fmt:message key="number.free.places" bundle="${rb}"/> ${faculty.numberFreePlaces}</p>
        <p><fmt:message key="number.pay.places" bundle="${rb}"/> ${faculty.numberPayPlaces}</p
        <h1>   aaa</h1>
    </c:forEach>

    <%--    <p><fmt:message key="number.applications.faculty" bundle="${rb}"/> ${appliationsFaculty}</p>--%>
    <%--    <p><fmt:message key="number.equal.you" bundle="${rb}"/> ${numberEqualYou}</p>--%>
    <%--    <p><fmt:message key="number.more.you" bundle="${rb}"/> ${numberMoreYou}</p>--%>


    <c:if test="${user!=null}"><a href="controller?command=logout"><fmt:message key="logout"
                                                                                bundle="${rb}"/> </a></c:if>
    <c:if test="${user==null}"><a href="controller?command=login"></a><fmt:message key="login" bundle="${rb}"/></c:if>

</form>

</body>
</html>
