<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="prop.internalization.jsp.signUp" var="signUpBundle"/>

<html>
<head>
</head>
<body>
<jsp:forward page="${pageContext.request.contextPath}/controller?command=main_get"></jsp:forward>

</body>
</html>

