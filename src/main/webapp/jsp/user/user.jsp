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
			<c:if test="${order==null}">
				<li><a href="BikeRentalServlet?command=create_order"><fmt:message key="createOrderLabel"/></a></li>
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
	<c:if test="${order!=null}">
	<form action="BikeRentalServlet" method="post" id="bikeOrder">
		<div class="container" style="margin-top: 15px">
			<div align="center" style="border-bottom: 1px solid blue; font-size: 25px; padding-bottom: 5px">
				<c:out value="${currentOrderLabel}"/>:
			</div>
			<div class="row" style="padding-top: 5px; padding-left: 5px">
				<div class="col-md-6"  style="padding-top: 5px; margin-left: 10px; background: white">
					<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
						<div class="col-md-6">
							<fmt:message key="bikeNubmerLabel"/> :
						</div>
						<div class="col-md-6">
							<c:out value="${order.bike.id}"></c:out>
						</div>
					</div>

					<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
						<div class="col-md-6">
							<c:out value="${brandLabel}"></c:out> <c:out value="${modelLabel}"></c:out>:
						</div>
						<div class="col-md-6">
							<c:out value="${order.bike.brand}"></c:out> <c:out value="${bikeOrder.bike.model}"></c:out>
						</div>
					</div>

					<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
						<div class="col-md-6">
							<c:out value="${characteristicsLabel}"></c:out>:
						</div>
						<div class="col-md-6">
							<c:out value="${order.bike.bikeType.type}"></c:out>,
						</div>
					</div>

					<div class="row" style="margin-top: 10px; padding-bottom: 10px; border-bottom: 1px solid gray;">
						<div class="col-md-6">
							<c:out value="${startParkingLabel}"></c:out>:
						</div>
						<div class="col-md-6">
							<c:out value="${order.bike.rentalPoint.name}"></c:out>
						</div>
					</div>
				</div>
			</div>


			<div align="center" style="border-bottom: 1px solid gray; font-size: 20px; margin-top:20px">
				<c:out value="${timeOfOrderLabel}"></c:out>:
			</div>

			<div class="row" style="margin-top:10px">


				<div class="col-md-2"></div>
				<div class="col-md-2" style="font-size: 20px">
					<label style="float:left"><c:out value="${daysLabel}"></c:out>: </label>
					<label style="float:left;" id="days">0</label>
				</div>

				<div class="col-md-2" style="font-size: 20px">
					<label style="float:left;"><c:out value="${hoursLabel}"></c:out>: </label>
					<label style="float:left;" id="hours">0</label>
				</div>

				<div class="col-md-2" style="font-size: 20px">
					<label style="float:left;"><c:out value="${minutesLabel}"></c:out>: </label>
					<label style="float:left;" id="minutes">0</label>
				</div>

				<div class="col-md-2" style="font-size: 20px">
					<label style="float:left;"><c:out value="${secondsLabel}"></c:out>: </label>
					<label style="float:left;" id="seconds">0</label>
				</div>
			</div>

			<div align="center" style="border-bottom: 1px solid gray; font-size: 20px; margin-top:20px">
				<c:out value="${closeOrderLabel}"></c:out>:
			</div>
		</div>

		<div align="center" style="margin-top:20px">
			<input type="hidden" name="command" value="close_order"/>
			<input type="hidden" name="orderId" value="${order.id}"/>
			<input type="hidden" name="rentPrice" value="${order.sum}"/>
			<input type="submit" class="btn btn-primary" style="background-color: green" value="${returnBikeLabel}">
		</div>
	</form>
	</c:if>

	<div style="margin-top: 20px">
		<%@ include file="/WEB-INF/jspf/editUser.jspf"%>
	</div>

	<%@ include file="/WEB-INF/jspf/message.jspf" %>

	<jsp:include page="/jsp/static/footer.jsp"/>
</body>
</html>