<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	
	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:300px; margin:0 auto;">
				<form class="form-signin" action = "${pageContext.request.contextPath}/customer/performMerchantTransaction" method = "post" >
				<div>				  						  			    
			        <label for = "textboxuser" > Enter username of Merchant</label> 
			        <input id = "textboxuser" class="form-control" type="text"  name = "userNametext" />    
		        </div>            
		        <div>
			        <label for = "textboxuser" > Enter amount </label> 
			        <input id = "textboxuser" class="form-control" type="text"  name = "amounttext" />                            
    	    	</div>	 
    	    	<br/>
    	     	 <div class ="pull-right">
				   			<button class="btn btn-large btn-primary" type="submit" value="submit" >Confirm transaction</button>
				   		</div>
				 </form>
				 	 
			</div>
		</div>
	</div> 	
</div>
</body>
</html>




