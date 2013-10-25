<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Transactions</title>
</head>
<body>
<h3>
	Welcome , Below are the your all transactions:
</h3>

<c:choose>
<c:when test="${not empty listTransactions}">
<table border="1">
<tr>
<td>From</td>
<td>FromAccount </td>
<td>To</td>
<td>ToAccount</td>
<td>Amount</td>
</tr>

<c:forEach var="transaction" items="${listTransactions}">
<tr>
<td>${transaction.fromCustomer}</td>
<td>${transaction.fromaccount}</td>
<td>${transaction.toCustomer}</td>
<td>${transaction.toacccount}</td>
<td>${transaction.amount}</td>
</tr>
</c:forEach>
</table>
</c:when>
<c:otherwise>
	There are no transactions to handle !
</c:otherwise>
</c:choose>


</body>
</html>    
