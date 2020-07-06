<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="rb"/>

<fmt:setLocale value="en" scope="session"/>
<html>
<head>
</head>
'
<body>
<%--<jsp:forward page="/jsp/signIn.jsp"></jsp:forward>--%>

<form name="aaa" action="${pageContext.request.contextPath}/www" method="post">

    <p><fmt:message key="login" bundle="${rb}"/> <input type="text" name="login" value=""/></p>
    <p><fmt:message key="email" bundle="${rb}"/> <input type="text" name="email" value=""/></p>


    <form action="upload" enctype="multipart/form-data" method="post">
        Upload file: <input type="file" name="content" height="130">
        <input type="submit" value="upload file">
    </form>

</form>

<%--<form name="loginForm" method="post" action="${pageContext.request.contextPath}/controller">--%>
<%--    <input type="hidden" name="command" value="sign_up">--%>
<%--    <input type="submit" value="upload file">--%>

<%--    <p><fmt:message key="login" bundle="${rb}"/> <input type="text" name="login" value="${login}"/>${errors.login}</p>--%>
<%--    <p><fmt:message key="email" bundle="${rb}"/> <input type="text" name="email" value="${email}"/>${errors.email}</p>--%>

<%--    <input type="submit" value="<fmt:message key="submit.signUp" bundle="${rb}"/>">--%>
<%--</form>--%>
${upload_result}

</body>
</html>

