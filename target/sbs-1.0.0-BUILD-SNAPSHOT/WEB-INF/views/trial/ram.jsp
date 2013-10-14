<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hi guys !
</h1>

<P>  This is a test page.... </P>
<c:if test="${not empty User }">Hellooooooooo from db. The name fetched from db is <c:out value="${User.name}"></c:out></c:if>
</body>
</html>
