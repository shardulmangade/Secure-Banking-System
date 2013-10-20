<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Sun Devil Bank</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/sbs/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/sbs/resources/css/custom.css" />
</head>
<body>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sbs/resources/js/bootstrap.js"></script>

<div class="container ">
	<header class="page-header"><!-- We can add navigation bar here--><h1>Welcome to Sun Devil Bank</h1></header>
	<!-- End of header -->
	
	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:300px; margin:0 auto;">
				<form class="form-signin">
					<fieldset>
				  		<h2 class="form-signin-heading">Please sign in</h2>
				  		<input type="text" class="form-control" placeholder="UserName">
				  		<br/>
				   		<input type="password" class="form-control" placeholder="Password">
				   		<br/>
				   		<div class ="pull-right">
				   			<button class="btn btn-large btn-primary" type="submit">Sign in</button>
				   		</div>
				   	</fieldset>
				 </form>	 
			</div>
		</div>
	</div> 	
	<!-- End of main content -->
	<footer class = "container"> 
		<em> copyright @Group 4, for more information do not hack this site ! </em>
	</footer>
	<!--  end of footer -->
</div>
</body>
</html>
