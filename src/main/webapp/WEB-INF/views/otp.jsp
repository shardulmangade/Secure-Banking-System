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
	Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
	<h3>Welcome, ${username}</h3>
	<c:choose>
	<c:when test="${not empty newOTP}">We have emailed you a <b>new</b> One Time Password (OTP) to your registered email address. Please enter it below:</c:when>
	<c:when test="${not empty errorOTP}">The OTP you entered did not match the one in our database. Please try again:</c:when>
	<c:otherwise>
	To access your account we need the One Time Password (OTP) which we
	sent to your registered email address. Please enter it below:
	</c:otherwise>
	</c:choose>
	<br>
	<br>
	<div class="row" id="main-content">
		<div class="span4 hero-unit" id="sidebar">
			<div class="well" style="width: 300px; margin: 0 auto;">
				<form class="form-signin"
					action="<c:url value='/auth/otp' />" method='POST'>
					<fieldset>
						<table>
							<tr>
								<td>OTP:</td>
								<td><input type='text' name='otp' autocomplete="off"></td>
							</tr>
							<tr>
								<td></td>
								<td><button class="btn btn-large btn-primary" type="submit">Submit
										OTP</button></td>
							</tr>
						</table>
					</fieldset>
				</form>
			</div>
		</div>
	</div>



</body>
</html>
