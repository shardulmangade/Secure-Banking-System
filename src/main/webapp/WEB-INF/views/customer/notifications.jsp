<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Sun Devil Bank</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/sundevilbank/resources/css/bootstrap.min.css" type="text/css"/>
	<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" type="text/css" />
</head>
<body>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>
<script src="/sundevilbank/resources/js/custom.js"></script>
<div class="container">
	<h2>Notifications</h2>
	<form:form method="POST" commandName="signupuser"  action="${pageContext.request.contextPath}/customers/grantaccess">
	<c:choose>
		<c:when test="${not empty notifications}">
			<table border="1">
			<tr>
				<th> Requested By </th>
				<th> Action </th>
			</tr>
			<c:forEach var="request" items="${notifications}">
				<tr>
					<td>${request.requestedBy}</td>
					<td> <input type="checkbox" name="iuser" value="${request.requestedBy}"> </td>
				<tr>
				</c:forEach>
			</table>
			<button class="btn btn-large btn-primary" name="action" type="submit" value="approve">Grant Permission</button>
		</c:when>
		<c:otherwise>
			No notifications so far...
		</c:otherwise>
	</c:choose>
	</form:form>
	
	
	
</div>
</body>
</html>