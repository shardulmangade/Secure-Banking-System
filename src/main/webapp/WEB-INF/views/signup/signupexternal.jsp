<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Registration Form</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>

</head>
<body>
<P>  Registration Form for External Users </P>
<form:form method="POST" commandName="signupuserexternal"  action="${pageContext.request.contextPath}/signupuser/signupPost.html" >

 <table>
    <tr>
        <td><form:label path="firstName">FirstName</form:label></td>
        <td><form:input path="firstName"/></td>
        <td><form:errors path="firstName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="lastName">LastName</form:label></td>
        <td><form:input path="lastName" /></td>
        <td><form:errors path="lastName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="userName">userName</form:label></td>
        <td><form:input path="userName" /></td>
        <td><form:errors path="userName" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="emailId">EmailId</form:label></td>
        <td><form:input path="emailId" /></td>
        <td><form:errors path="emailId" cssClass="error" /></td>
    </tr>
    <tr>
        <td><form:label path="ssn">SSN</form:label></td>
        <td><form:input class = "form-control" path="ssn" /></td>
        <td><form:errors path="ssn" cssClass="error" /></td>
    </tr> 
    
    <tr>   	    
   	    <td> Department : </td> <%-- SHOULD THERE NOT BE A <FORM:LABEL> TAG, IT LOOKS DIFF IN UI --%>
   	    <td> <form:select path = "department">
   	     <form:option value="NONE" label="--- Select ---" />
   	     <form:options items="${departmentList}"/>
   	     </form:select>  	    
   	     <td><form:errors path="department" cssClass="error" /></td>
       
    </tr>
    <tr>
    <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
    
<!--

<FORM METHOD=POST ACTION="saveData"> 
First Name <INPUT TYPE=TEXT NAME=firstName SIZE=20> <BR>
Last Name <INPUT TYPE=TEXT NAME=lastName SIZE=20>  <BR>
UserName <INPUT TYPE=TEXT NAME=userName SIZE=20>   <BR>
Email Id <INPUT TYPE=TEXT NAME=emailId SIZE=20>   <BR>
DateOfBirth <INPUT TYPE=TEXT NAME=dateOfBirth SIZE=20>  

<P><INPUT TYPE=SUBMIT>
</FORM>
</body>
</html>-->