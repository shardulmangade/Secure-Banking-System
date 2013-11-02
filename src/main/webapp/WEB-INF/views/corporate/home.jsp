<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
	<title>Sun Devil Bank</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/sundevilbank/resources/css/bootstrap.min.css" type="text/css"/>
	<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" type="text/css" />
</head>
<body>

<sec:authorize access="hasAnyRole('ROLE_VALID_USER','ROLE_CORPORATE_MANAGER','ROLE_IT_EMPLOYEE','ROLE_IT_MANAGER','ROLE_HR_EMPLOYEE','ROLE_HR_MANAGER','ROLE_SALES_EMPLOYEE','ROLE_SALES_MANAGER','ROLE_EXTERNAL_USER','ROLE_TRANSACTION_EMPLOYEE','ROLE_TRANSACTION_MANAGER','ROLE_EXTERNAL_MERCHANT')">
<a href="${pageContext.request.contextPath}/home"> Home </a>| Want to leave ${username}?<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
</sec:authorize>

<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>
<script src="/sundevilbank/resources/js/custom.js"></script>
<div class="container">
Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
	<header class="page-header"><!-- We can add navigation bar here--><h1>Welcome Manager</h1>	</header>
	<!-- End of header -->
	<div class = "row" id="main-content">	
    	<table style="width:100%">
    		<tr>
    			<td width="15%">
    				<div class="sidebar-nav-fixed">
		        		<div class="well">         
				            <ul class="nav nav-list">				
								<li><a  class= "active" style="cursor: pointer;"  onclick="changeContent(1, '/sundevilbank/corporate/op1/')"> Add </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '/sundevilbank/corporate/op3')"> Transfer </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '/sundevilbank/corporate/op4')"> View Pending Request </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '/sundevilbank/corporate/allactivemanagers')"> Deactivate Manager </a></li>
							</ul>
						</div>
					</div>
    			</td>
    			<td width = "15%"></td>
    			<td  width="70%">
	   				<div class="hero-unit" id= "1">
					</div>
    			</td>
    		</tr>
    		
    	</table>
	</div> 	
	<!-- End of main content -->
	<footer class = "container"> 
		<em> copyright @Group 4, for more information do not hack this site ! </em>
	</footer>	
</div>
</body>
</html>