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
<div class="container ">	
	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:500px;">
				<form class="form-signin" method = "post" action = "/sundevilbank/corporate/corporatedelete">			  						  			    
			        <label for = "textboxuser"> Enter username of employee to be deleted:</label> 
			        <br/>
			        <input id = "textboxuser" class="form-control" type="text"  name = "userNametext" />                
    	     	 	<br/>
    	     	 	<div class ="pull-right">
				   		<button class="btn btn-large btn-primary" type="submit" value="submit">Submit</button>
				   	</div>
				   	<br/>
				 </form>
				 	 
			</div>
		</div>
	</div> 
</div>
</body>
</html>
