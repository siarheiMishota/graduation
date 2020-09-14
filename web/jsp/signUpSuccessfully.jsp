<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="signUpSuccessfullyBundle"/>

<html>
<head>
    <title>Successfully</title>
</head>
<body>
<h3><fmt:message key="success" bundle="${signUpSuccessfullyBundle}"/>${login}</h3>
<a href="${pageContext.request.contextPath}/controller?command=login_get"><fmt:message key="href" bundle="${signUpSuccessfullyBundle}"/> </a>
</body>
</html>
