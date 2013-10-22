<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Sun Devil Bank</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="/sundevilbank/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" />
</head>
<body>	
	<h3>Welcome,  ${username}</h3>	
 
	<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
</body>
</html>
