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
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="/sundevilbank/resources/js/bootstrap.js"></script>

<div class="container ">
	<header class="page-header"><!-- We can add navigation bar here--><h3>Delete employee form</h3></header>
	<!-- End of header -->
	
	<div class = "row" id="main-content">
		<div class = "span4 hero-unit" id="sidebar">
			<div class="well" style="width:300px; margin:0 auto;">
				<form class="form-signin">
				  						  			    
        <label for = "textboxuser"> Enter username of employee to be deleted</label> 
        <input id = "textboxuser" class="form-control" type="text"  name = "userNametext" />                
    	     	 
    	     	 <div class ="pull-right">
				   			<button class="btn btn-large btn-primary" type="submit" value="submit">Submit</button>
				   		</div>
				 </form>
				 	 
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



<%-- <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<P>  Delete employee form </P>


<form:form  method="POST"   action="${pageContext.request.contextPath}/signupuser/deleteemployee.html" >

 <table>
    <tr>
        <td><label for = "textboxuser"> Enter username of employee to be deleted</label> 
        <td><input id = "textboxuser" type="text"  name = "userNametext" /></td>        
        <td><form:errors  cssClass="error" /></td>
    </tr>
   
    <tr>
    <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
--%>  
