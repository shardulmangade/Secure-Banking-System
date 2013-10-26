<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Sun Devil Bank - User Home</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="/sundevilbank/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" />
</head>
<body>

Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
	<h3>Welcome, ${username}. You are one of our most valued merchant.</h3>
	
<!-- End of main content -->
		<footer class="container">
			<em> copyright @Group 4, for more information do not hack this
				site ! </em>
		</footer>
		<!--  end of footer -->
	</div>
</body>
</html>