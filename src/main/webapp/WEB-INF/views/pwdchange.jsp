<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Signup Page</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>

</head>
<body>
<P>  Password Change : Both text fields should match and not be empty. </P>
<br>
<form:form method="POST" action="${pageContext.request.contextPath}/pwdchange" >

 <table>
    <tr>
        <td><form:label path="newPassword">New Password: </form:label></td>
        <td><form:input type="password" class = "form-control" path="newPassword"/></td>
        <td><form:errors path="newPassword" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="confirmNewPassword">Confirm Password: </form:label></td>
        <td><form:input type="password" class = "form-control" path="confirmNewPassword" /></td>
        <td><form:errors path="confirmNewPassword" cssClass="error" /></td>
   
    <tr>
	    <td colspan="2">
	    	<div class ="pull-right">
				<button class="btn btn-large btn-primary" type="submit" value="submit">Submit</button>
			</div>
	          
	    </td>
    </tr>
</table>  
</form:form> 
