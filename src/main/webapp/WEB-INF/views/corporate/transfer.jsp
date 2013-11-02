<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<div class="container">
	<P> Corporate Employee Transfer Form </P>
	<form:form method="POST" commandName="signupemployee"  action="${pageContext.request.contextPath}/corporate/corporateUpdate.html" >
	
     <label for = "textboxuser"> Enter username of HR employee to be transfered</label> 
        <input id = "textboxuser" class="form-control" type="text"  name = "userNametext" />   	
  <table> 
   <tr>   	    
   	    <td> Select Department to which you want to transfer : </td> <%-- SHOULD THERE NOT BE A <FORM:LABEL> TAG, IT LOOKS DIFF IN UI --%>
   	    <td> <form:select path = "department">
   	     <form:option value="NONE" label="--- Select ---" />
   	     <form:options items="${departmentList}"/>
   	     </form:select>  	    
   	     <td><form:errors path="department" cssClass="error" /></td>       
    </tr> 
     <tr>   	    
   	    <td> Select Role which you want to give  : </td> <%-- SHOULD THERE NOT BE A <FORM:LABEL> TAG, IT LOOKS DIFF IN UI --%>
   	    <td> <form:select path = "role">
        <form:option value="NONE" label="--- Select ---" />
        <form:options items="${roleList}"/>
        </form:select>  	    
        <td><form:errors path="role" cssClass="error" /></td>
       
    </tr>     
    <tr>
	    <td colspan="2">
	    	<div class ="pull-right">
				<button class="btn btn-large btn-primary" type="submit" value="submit">Submit</button>
			</div>
	          
	    </td>
    </tr>
</table>  
</form:form> 
</div>
</body>
</html>
