<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<style>
		<%@include file="/resources/css/bootstrap.min.css"%>
		<%@include file="/resources/css/commonStyles.css"%>
		<%@include file="/resources/css/header.css"%>
	</style>

	<%@ include file="../../jspf/localizationVar.jspf" %>
	
	<title><fmt:message key="title"/> </title>
</head>

<body>

	<c:set var="menuLabel" value="${errorLabel}" scope="page"/> 
	<%@ include file="/WEB-INF/jspf/smallMenu.jspf" %>
	
	<section>
	    <div style="padding: 20px">
	        <h1>Error</h1> 
	        <p><b>Error code:</b> ${pageContext.errorData.statusCode}</p>
	        <p><b>URL:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
	        <p><b>Exception type:</b> ${requestScope['javax.servlet.error.exception_type']}</p>
	        <p><b>Exception message:</b> ${requestScope['javax.servlet.error.message']}</p>
			<p><b>Status code: ${pageContext.errorData.statusCode}</b></p>
	        <p><b>Exception:</b> ${pageContext.exception.message}</p>
	        <p><b>Servlet name:</b> ${pageContext.errorData.servletName}</p>
	    </div>
	</section>
	<jsp:include page="/WEB-INF/jsp/static/footer.jsp"/>
</body>
</html>
