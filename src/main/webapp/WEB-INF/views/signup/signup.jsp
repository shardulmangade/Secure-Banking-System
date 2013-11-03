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
<P>  Registration Form </P>
<form:form method="POST" commandName="signupuser"  action="${pageContext.request.contextPath}/hr/hremployee/SignupEmployeePost.html" >

 <table>
    <tr>
        <td><form:label path="userName">userName</form:label></td>
        <td><form:input path="userName" /></td>
        <td><form:errors path="userName" cssClass="error" /></td>
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