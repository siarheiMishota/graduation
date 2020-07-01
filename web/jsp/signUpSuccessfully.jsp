<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="rb"/>
<html>
<head>
    <title>Successfully</title>
</head>
<body>
<h3><fmt:message key="success" bundle="${rb}"/>${login}</h3>
<a href="/jsp/signIn.jsp"><fmt:message key="href" bundle="${rb}"/> </a>
</body>
</html>
