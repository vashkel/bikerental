<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<style>
		<%@include file="/resources/css/bootstrap.min.css"%>
		<%@include file="/resources/css/header.css"%>
	</style>

	<%@ include file="/WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><fmt:message key="title"/> </title>

</head>
<body>
	<!-- ------------------------------ menu ----------------------------------- -->
	<nav>	
		<ul>
			<li class="logo"><fmt:message key="title"/></li>
			<c:if test="${bikeOrder==null}">
				<li><a href="BikeRentalServlet?command=go_to_order_page"><fmt:message key="createOrderLabel"/></a></li>
			</c:if>
			<li><a href="BikeRentalServlet?command=find_user_orders"><fmt:message key="ordersLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=rental_points_page"><fmt:message key="contacts"/></a></li>
			<li style="float: right; margin-right: 50px"><a href="#"><c:out value="${user.name}"></c:out></a>
				<ul>
					<li><a href="#" onclick="showProfile()"><fmt:message key="profileLabel"/></a></li>
					<li><a href="#" onclick="showPassword()"><fmt:message key="passwChangeLabel"/></a></li>
					<li><a href="BikeRentalServlet?command=logout"><fmt:message key="logoutLabel"/></a></li>
				</ul>
			</li>
			<li style="float: right; color: white; padding: 18px 0px 15px 10px;"><fmt:message key="userLabel"/></li>
		</ul>
	</nav>
	<!-- --------------------------end of menu -------------------------------- -->

	<div style="margin-top: 20px">
		<%@ include file="/WEB-INF/jspf/editUser.jspf"%>
	</div>

	<%@ include file="/WEB-INF/jspf/message.jspf" %>

	<jsp:include page="/jsp/static/footer.jsp"/>
</body>
</html>