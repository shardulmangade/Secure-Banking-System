<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<a href="${pageContext.request.contextPath}"> Home </a> 
<sec:authorize
	access="hasAnyRole('ROLE_VALID_USER','ROLE_CORPORATE_MANAGER','ROLE_IT_EMPLOYEE','ROLE_IT_MANAGER','ROLE_HR_EMPLOYEE','ROLE_HR_MANAGER','ROLE_SALES_EMPLOYEE','ROLE_SALES_MANAGER','ROLE_EXTERNAL_USER','ROLE_TRANSACTION_EMPLOYEE','ROLE_TRANSACTION_MANAGER','ROLE_EXTERNAL_MERCHANT')">
	| Want to leave ${username}?<a
		href="<c:url value="/j_spring_security_logout" />"> Logout</a><br><br>
</sec:authorize>
<header>
	<h2>Looks like you are snooping around !</h2>
	<span class="byline">Access Error</span>
</header>

<section>
	<c:choose>
		<c:when test="${not empty ex_message}">
		<c:out value="${ex_message}"></c:out></c:when>
		<c:otherwise>
	Sorry, You don't have permission to access this resource.
	</c:otherwise>
	</c:choose>
</section>