<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	<!-- End of header -->
	<div class = "row" id="main-content">	
    	<table style="width:100%">
    		<tr>
    			<td width="15%">
    				<div class="sidebar-nav-fixed">
		        		<div class="well">         
				            <ul class="nav nav-list">				
								<li><a href= "#" class= "active"> Ask Permission </a></li>
								<li><a style="cursor: pointer;" onclick="changeContent(1, '/sundevilbank/signupuser/deleteemployee/op1')"> Delete </a></li>
								<li><a href= "#"> Transfer </a></li>
							</ul>
						</div>
					</div>
    			</td>
    			<td width = "15%"></td>
    			<td  width="70%">
	   				<div class="hero-unit">
						<p>
						<b>Welcome, You are on the manager at this ungodly time</b>
						</p>
						
						<br>
						transaction details are as follows:-
						<br>
									<p>
						<b>Welcome, You are on the manager at this ungodly time : ${serverTime} </b>
						</p>
						
						<br>
						transaction details are as follows:-
						<br>
						<c:choose>
							<c:when test="${not empty transactions}">
								<table border="1">
								<tr>
									<th> Transaction ID</th>
									<th> Description </th>
									<th> TimeStamp </th>
								</tr>
								<c:forEach var="request" items="${transactions}">
									<tr>
										<td>${request.transactionID}</td>
										<td>${request.description}</td>
										<td>${request.time}</td>
									<tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								No transactions so far...
							</c:otherwise>
						</c:choose>
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