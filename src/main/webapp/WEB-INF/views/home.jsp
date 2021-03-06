<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Sun Devil Bank</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="/sundevilbank/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/sundevilbank/resources/css/custom.css" />
</head>
<body>
	<script src="https://code.jquery.com/jquery-1.10.1.min.js"></script>
	<script src="/sundevilbank/resources/js/bootstrap.js"></script>

	<div class="container ">
		<header class="page-header">
			<!-- We can add navigation bar here-->
			<h1>Welcome to Sun Devil Bank</h1>
		</header>
		<!-- End of header -->

		<div class="row" id="main-content">
			<div class="span4 hero-unit" id="sidebar">
				<div class="well" style="width: 300px; margin: 0 auto;">
					<c:if test="${not empty error}">
						<div class="errorblock">
							Your login attempt was not successful, try again.<br /> Caused :
							${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
						</div>
					</c:if>
					<form class="form-signin"
						action="<c:url value='j_spring_security_check' />" method='POST'>
						<fieldset>
							<h2 class="form-signin-heading">Please sign in</h2>
							<table>
								<tr>
									<td>Username:</td>
									<td><input type='text' name='j_username'
										autocomplete="off"></td>
								</tr>
								<tr>
									<td>Password:</td>
									<td><input type='password' name='j_password'
										autocomplete="off" /></td>
								</tr>
							</table>
							<div class="pull-right">
								<button class="btn btn-large btn-primary" type="submit">Sign
									in</button>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
		<!-- End of main content -->
		<footer class="container">
			<em> copyright @Group 4</em>
		</footer>
		<!--  end of footer -->
	</div>
</body>
</html>
