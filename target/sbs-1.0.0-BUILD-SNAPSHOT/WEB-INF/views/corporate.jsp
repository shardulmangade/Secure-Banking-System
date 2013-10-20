<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ page session="false" %>
<html>
<head>
	<title>Corporate</title>
</head>
<body>
	 <form:form method='post' action='myForm' commandName='myUser'>
    <table>
      <tr>
        <td>Name: <font color='red'><form:errors path='name' /></font></td>
      </tr>
      <tr>
        <td><form:input path='name' /></td>
      </tr>
      <tr>
        <td>Age: <font color='red'><form:errors path='age' /></font></td>
      </tr>
      <tr>
        <td><form:input path='age' /></td>
      </tr>
      <tr>
        <td><input type='submit' value='Submit' /></td>
      </tr>
    </table>
  </form:form>
</body>
</html>