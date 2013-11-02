<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
<a href="${pageContext.request.contextPath}"> Home </a> 
	<h2>Looks like your account has been deactivated !</h2>
	<span class="byline">Access Error</span>
	<c:choose>
		<c:when test="${not empty ex_message}">
		<c:out value="${ex_message}"></c:out></c:when>
	</c:choose>
		
</header>
