<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
	<title>Sun Devil Bank</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/sundevilbank/resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" />
</head>
<body>
<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>

<div class="container ">
	<header class="page-header"><h1>I am spoofed Page</h1></header>

	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:300px; margin:0 auto;">
				<form:form class="form-signin" action="/sundevilbank/stuff" modelAttribute="subscriber">
					<fieldset>
				  		<label for="nameInput">Name: </label>
						<form:input path="name" id="nameInput" />
						<form:errors path="name" cssClass="error" />
						<br/>
						<label for="emailInput">Email: </label>
						<form:input path="email" id="emailInput" />
						<form:errors path="email" cssClass="error" />
						<br/>			
				   		<div class ="`">
				   			<button class="btn btn-large btn-primary" type="submit" value="submit">Test </button>
				   		</div>
				   	</fieldset>
				 </form:form>	 				
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