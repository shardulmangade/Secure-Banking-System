<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Transfer  Page</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>

</head>
<body>
<P> IT Employee Transfer Form </P>
<form:form method="POST"  commandName="signupemployee"  action="${pageContext.request.contextPath}/it/manager/transferemployee/op1.html" >

     <label for = "textboxuser"> Enter username of IT employee to be transfered</label> 
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




















<%-- <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a new HR employee</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>

</head>
<body>
<P>  HR Employee Registration Form </P>
 
 <form:form method="POST"  commandName="signupemployee"  action="${pageContext.request.contextPath}/signupemployee/newhremployee" >

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
        <td><form:label path="userName">userName</form:label></td>
        <td><form:input class = "form-control" path="userName" /></td>
        <td><form:errors path="userName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="emailId">emailId</form:label></td>
        <td><form:input class = "form-control" path="emailId" /></td>
        <td><form:errors path="emailId" cssClass="error" /></td>
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
--%>   
