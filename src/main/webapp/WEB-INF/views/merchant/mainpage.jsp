<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<title>Sun Devil Bank</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="/sundevilbank/resources/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css"
	type="text/css" />
</head>
<body>

	<sec:authorize access="hasAnyRole('ROLE_VALID_USER','ROLE_CORPORATE_MANAGER','ROLE_IT_EMPLOYEE','ROLE_IT_MANAGER','ROLE_HR_EMPLOYEE','ROLE_HR_MANAGER','ROLE_SALES_EMPLOYEE','ROLE_SALES_MANAGER','ROLE_EXTERNAL_USER','ROLE_TRANSACTION_EMPLOYEE','ROLE_TRANSACTION_MANAGER','ROLE_EXTERNAL_MERCHANT')">
	<a href="${pageContext.request.contextPath}/home"> Home </a>| Want to leave ${username}?<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
	</sec:authorize>
	<h3>Welcome, ${username}</h3>
	<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="/sundevilbank/resources/js/bootstrap.js"></script>
	<script src="/sundevilbank/resources/js/custom.js"></script>
	<div class="container">
		<header class="page-header">
			<!-- We can add navigation bar here-->
			<h1>Sun Devil Bank Customer(Merchant)</h1>
		</header>
		<!-- End of header -->
		<div class="row" id="main-content">
			<table style="width: 100%">
				<tr>
					<td width="15%">
						<div class="sidebar-nav-fixed">
							<div class="well">
								<ul class="nav nav-list">
									<li><a class="active" style="cursor: pointer;"
										onclick="changeContent(1, '../../merchant/merchant/transaction')">View
											all transactions </a></li>
									<li><a style="cursor: pointer;"
										onclick="changeContent(1, '../../merchant/merchant/newtransaction')">
											Make new Transfer Transaction </a></li>
									<li><a style="cursor: pointer;"
										onclick="changeContent(1, '../../merchant/merchant/clearCustomerPendingTransactions')">
											Pay Customer Transactions </a></li>
									<li><a style="cursor: pointer;"
										onclick="changeContent(1, '${pageContext.request.contextPath}/pwd')">
											Change Password </a></li>
								</ul>
							</div>
						</div>
					</td>
					<td width="15%"></td>
					<td width="70%">
						<div class="hero-unit" id="1"></div>
					</td>
				</tr>

			</table>
		</div>
		<!-- End of main content -->
		<footer class="container">
			<em> copyright @Group 4</em>
		</footer>
	</div>
</body>
</html>










<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HR Manager </title>
</head>

<h4>

HR Manager
</h4>
<body>	
    <a href="../../signupemployee/newhremployee">Add a new HR employee</a> <br> <br> <br>
    <a href="../../signupuser/signup/signup.jsp">Delete an HR employee</a> <br> <br> <br>		
    <a href="../../signupuser/signup/signup.jsp">Transfer an HR employee to another department</a> <br> <br> <br>
	
</body>
</html> 
--%>