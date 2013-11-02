<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
<P>  Employee Registration Form </P>
<form:form method="POST" commandName="signupuser"  action="${pageContext.request.contextPath}/corporate/corporateadduser.html" >

 <table>
    <tr>
        <td><form:label path="firstName">firstName</form:label></td>
        <td><form:input class = "form-control" path="firstName"/></td>
        <td><form:errors path="firstName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="lastName">lastName</form:label></td>
        <td><form:input class = "form-control" path="lastName" /></td>
        <td><form:errors path="lastName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="username">userName</form:label></td>
        <td><form:input class = "form-control" path="username" /></td>
        <td><form:errors path="username" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="email">emailId</form:label></td>
        <td><form:input class = "form-control" path="email" /></td>
        <td><form:errors path="email" cssClass="error" /></td>
    </tr>
    <tr>   	    
   	    <td><b> Department : </b></td> <%-- SHOULD THERE NOT BE A <FORM:LABEL> TAG, IT LOOKS DIFF IN UI --%>
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
        <td><form:label path="ssn">SSN(10 digit)</form:label></td>
        <td><form:input class = "form-control" path="ssn" /></td>
        <td><form:errors path="ssn" cssClass="error" /></td>
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
