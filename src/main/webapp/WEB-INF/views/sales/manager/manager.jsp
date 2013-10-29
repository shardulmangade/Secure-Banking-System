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
	Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>
<script src="/sundevilbank/resources/js/custom.js"></script>
<div class="container">
	<header class="page-header"><!-- We can add navigation bar here--><h1>Sales Manager</h1>	</header>
	<!-- End of header -->
	<div class = "row" id="main-content">	
    	<table style="width:100%">
    		<tr>
    			<td width="15%">
    				<div class="sidebar-nav-fixed">
		        		<div class="well">         
				            <ul class="nav nav-list">				
								<li><a  class= "active" style="cursor: pointer;"  onclick="changeContent(1, '${pageContext.request.contextPath}/sales/salesmanager/newsalesemployee')">Add a new Sales employee  </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '${pageContext.request.contextPath}/sales/salesmanager/deletesalesemployee/op1')"> Delete an employee </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '${pageContext.request.contextPath}/sales/salesmanager/transfersalesemployee')"> Transfer Sales employee</a></li>
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


