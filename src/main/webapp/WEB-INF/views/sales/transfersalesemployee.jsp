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
<P> Sales Employee Transfer Form </P>
<form:form method="POST"  commandName="signupemployee"  action="${pageContext.request.contextPath}/signupemployee/transfersalesemployee/op1.html" >

     <label for = "textboxuser"> Enter username of Sales employee to be transfered</label> 
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
	    <td colspan="2">
	    	<div class ="pull-right">
				<button class="btn btn-large btn-primary" type="submit" value="submit">Submit</button>
			</div>
	          
	    </td>
    </tr>
</table>  
</form:form> 


