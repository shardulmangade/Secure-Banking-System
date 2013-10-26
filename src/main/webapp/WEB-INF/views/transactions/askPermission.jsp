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
	<h2>Transactions</h2>
	
	
	<c:choose>
		<c:when test="${not empty users}">
			<table border="1">
			<tr>
				<th> UserName </th>
				<th> Action </th>
			</tr>
			<c:forEach var="request" items="${users}">
				<tr>
					<td>${request}</td>
					<td> Aprrove or Deny</td>
				<tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			No Users
		</c:otherwise>
	</c:choose>
	
	
	
</div>
</body>
</html>