<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<style>
			<%@include file="/resources/css/bootstrap.min.css"%>
			<%@include file="/resources/css/commonStyles.css"%>
			<%@include file="/resources/css/labelStyle.css"%>
			<%@include file="/resources/css/header.css"%>
		</style>
		<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
		
		<title><fmt:message key="title"/> </title>
	</head>
	<body>
	
		<c:set var="menuLabel" value="${addBikeLabel}" scope="page"/>
		<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
		<form action="BikeRentalServlet" method="post" id="addBikeForm" >

			<div class="col-md-8" style="float: left">
				<!--   ---------------------------------- bike brand ------------------------------------------ -->
				<div class="row">
					<div class="col-md-6">
						<label><fmt:message key="brandLabel"/> *</label>
					</div>
					<div class="col-md-5">
						<select	name="brand" id="brand" class="form-control" style="width: 330px; float: left;">
							<option>${chooseBrandLabel}</option>
							<c:forEach var="brand" items="${brandList}">
								<option  value="${brand}"><fmt:message key="brandLabel"/> </option>
							</c:forEach>
						</select>
					</div>
				</div>
				<!--   ---------------------------------- model ------------------------------------------ -->
				<div class="row">
					<div class="col-md-6">
						<label><fmt:message key="modelLabel"/> *</label>
					</div>
					<div class="col-md-5">
						<input type="text" id="model" name="model" class="form-control">
					</div>
				</div>
				<!--   ---------------------------------- bike type ------------------------------------------ -->
				<div class="row">
					<div class="col-md-6">
						<label><fmt:message key="bikeTypeLabel"/> *</label>
					</div>
					<div class="col-md-5">
						<select	name="bikeTypeId" id="bikeTypeId" class="form-control" style="width: 330px; float: left;">
							<option>
								<fmt:message key="chooseBikeTypeLabel"/>
							</option>
							<c:forEach var="item" items="${bikeTypeList}">
								<option value="${item.id}">
									<c:out value="${item.type}"/>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label><fmt:message key="rentalPointLabel"/>*</label>
					</div>
					<div class="col-md-5">
						<select	name="rentalPointId"  id="rentalPointId" class="form-control" style="width: 330px; float: left;">
							<option>
								<fmt:message key="chooseRentalPoint"/>
							</option>
							<c:forEach var="item" items="${rentalPointList}">
								<option value="${item.id}">
									<c:out value="${item.id}"/>
									<c:out value="${item.name}"/>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<!--   ---------------------------------- submit block ---------------------------------------- -->
				<div class="col-md-5" align="center">
					<input id="command" type="hidden" name="command" value="add_bike" />
					<input type="submit" class="btn btn-primary" value="${addBikeLabel}"/>
				</div>
			</div>
		</form>

		<%@ include file="../../WEB-INF/jspf/message.jspf" %>
		<jsp:include page="/jsp/static/footer.jsp"/>
	</body>
</html>