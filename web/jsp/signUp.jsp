<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="rb"/>

<html>
<head>
    <title><fmt:message key="title" bundle="${rb}"/></title>
</head>
<body>

<form name="loginForm" method="post" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="sign_up">

    <p><fmt:message key="login" bundle="${rb}"/> <input type="text" name="login" value="${login}"/>${errors.login}</p>
    <p><fmt:message key="email" bundle="${rb}"/> <input type="text" name="email" value="${email}"/>${errors.email}</p>
    <p><fmt:message key="password" bundle="${rb}"/> <input type="password" name="password" value=""/>${errors.password}</p>
    <p><fmt:message key="surname" bundle="${rb}"/> <input type="text" name="surname" value="${surname}"/>${errors.surname}</p>
    <p><fmt:message key="firstName" bundle="${rb}"/> <input type="text" name="firstName" value="${firstName}"/>${errors.firstName}</p>
    <p><fmt:message key="fatherName" bundle="${rb}"/> <input type="text" name="fatherName" value="${fatherName}"/>${errors.fatherName}</p>
    <p><fmt:message key="passportId" bundle="${rb}"/> <input type="text" name="passportId" value="${passportId}"/>${errors.passportId}</p>
    <p><fmt:message key="birth" bundle="${rb}"/> <input type="date" name="birth" value="${birth}"/>${errors.birth}</p>
    <p><fmt:message key="gender" bundle="${rb}"/>
        <input type="radio" name="gender" value="male"> <fmt:message key="male" bundle="${rb}"/>
        <input type="radio" name="gender" value="female"> <fmt:message key="female" bundle="${rb}"/>
    </p>
    ${errorLoginPassMessage} <br/>
    <input type="submit" value="<fmt:message key="submit.signUp" bundle="${rb}"/>">
</form>

</body>
</html>
