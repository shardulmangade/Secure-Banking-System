<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<a href="${pageContext.request.contextPath}"> Home </a> 
<sec:authorize access="hasAnyRole('ROLE_VALID_USER','ROLE_CORPORATE_MANAGER','ROLE_IT_EMPLOYEE','ROLE_IT_MANAGER','ROLE_HR_EMPLOYEE','ROLE_HR_MANAGER','ROLE_SALES_EMPLOYEE','ROLE_SALES_MANAGER','ROLE_EXTERNAL_USER','ROLE_TRANSACTION_EMPLOYEE','ROLE_TRANSACTION_MANAGER','ROLE_EXTERNAL_MERCHANT')">
	 | Want to leave ${username}?<a
		href="<c:url value="/j_spring_security_logout" />"> Logout</a><br><br>
</sec:authorize>

<header>
	<h2>You seem to be lost! Or snooping around? You will be automatically redirected in few seconds...</h2>
</header>

404 Error - Page not found
<% response.setHeader("Refresh", "8;/sundevilbank"); %>