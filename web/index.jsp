<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="en" scope="session"/>
<html>
<head>
</head>
<body>
<%--<jsp:forward page="/jsp/signIn.jsp"></jsp:forward>--%>

<form action="upload" enctype="multipart/form-data" method="post" >
    Upload file: <input type="file" name="content" height="130">
    <input type="submit" value="upload file">
</form>
${upload_result}

</body>
</html>

