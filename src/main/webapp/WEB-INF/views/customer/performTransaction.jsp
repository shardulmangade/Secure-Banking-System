<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Perform transaction page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/sundevilbank/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" />
</head>
<body>
	
<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>

<div class="container ">
	<header class="page-header"><!-- We can add navigation bar here--><h1> Status Of transaction is</h1></header>
	<!-- End of header -->
	
	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:300px; margin:0 auto;">
				 <P> ${message}</P>
			</div>
		</div>
	</div> 	
	<!-- End of main content -->
	<footer class = "container"> 
		<em> copyright @Group 4 </em>
	</footer>
	<!--  end of footer -->
</div>
<% response.setHeader("Refresh", "10;/sundevilbank/home"); %>
</body>
</html>





























