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
<P>  Delete employee form </P>
<%--   <form:form method="POST" commandName ="deleteemployee"  action="${pageContext.request.contextPath}/signupuser/deleteemployee.html" >

 <table>
    <tr>
        <td><form:label path="userName">Enter username of employee to be deleted</form:label></td>
        <td><form:input path="userName"  name = "userNametext" /></td>
        <td><form:errors path="userName" cssClass="error" /></td>
    </tr>
   
    <tr>
    <td colspan="2">
            <input type="submit" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>  --%>  


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
