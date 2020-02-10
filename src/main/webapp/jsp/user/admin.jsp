<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<%@include file="/resources/css/header.css"%>
	</style>
	
	<%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><fmt:message key="title"/> </title>
	
</head>
<body>

	<h1 class="text-white"><fmt:message key="title"/></h1>
	<nav>	
		<ul>
			<li
					class="logo"><fmt:message key="title"/>
			</li>

			<li><a href="BikeRentalServlet?command=get_all_user"><fmt:message key="usersLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=bikeCatalog"><fmt:message key="bikeCatalogLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=rental_points_page"><fmt:message key="rentalPointsLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=go_to_order_report_page"><fmt:message key="ordersLabel"/></a></li>

			<li style="float: right; margin-right: 50px"><a href="#"><c:out value="${user.name}"></c:out></a>
				<ul>
					<li><a href="#" onclick="showProfile()"><fmt:message key="profileLabel"/></a></li>
					<li><a href="#" onclick="showPassword()"><fmt:message key="passwChangeLabel"/></a></li>
					<li><a href="BikeRentalServlet?command=logout"><fmt:message key="logoutLabel"/></a></li>
				</ul>
			</li>
			<li
					style="float: right; color: white; padding: 18px 0px 15px 10px;"><fmt:message key="adminLabel"/>
			</li>
			
		</ul>
	</nav>
		<div id="calend" style="margin-top: 20px">
			<%@ include file="../../WEB-INF/jspf/editUser.jspf" %>
		</div>
		
		<div id="body" style="margin: 20px">
			<c:if test="${usersList!=null}">
				<div style="text-align:center; margin-bottom:10px; font-size: 28px">
					<c:out value="${usersLabel}">:</c:out>
				</div>
				
				<div style="margin-left: 20px">
					<div class="row">
						<div class="col-md-12">
							<table class="table">
								<thead>
									<tr>
										<th><fmt:message key="loginLabel"/></th>
										<th><fmt:message key="nameLabel"/></th>
										<th><fmt:message key="surnameLabel"/></th>
										<th><fmt:message key="emailLabel"/></th>
										<th><fmt:message key="phoneLabel"/></th>
										<th><fmt:message key="roleLabel"/></th>
										<th><fmt:message key="stateLabel"/></th>
										<th><fmt:message key="openOrderLabel"/></th>
									</tr>
								</thead>
								<c:forEach items="${usersList}" var="item">
									<tbody>
										<tr>
											<td><c:out value="${item.login}"/></td>
											<td><c:out value="${item.name}"/></td>
											<td><c:out value="${item.surname}"/></td>
											<td><c:out value="${item.email}"/></td>
											<td><c:out value="${item.tel}"/></td>
											<td><c:out value="${item.role}"/></td>
											<td><c:out value="${item.state}"/></td>
											<td>
												<c:if test="${item.id!=0}">
													<c:out value="${item.id}"/>
												</c:if>
											</td>													
										</tr>									
									</tbody>
								</c:forEach>		
							</table>
						</div>		
					</div>
				</div>
			</c:if>
		</div>
	
	<%@ include file="../../WEB-INF/jspf/message.jspf" %>
	<jsp:include page="/jsp/static/footer.jsp"/>
</body>
</html>