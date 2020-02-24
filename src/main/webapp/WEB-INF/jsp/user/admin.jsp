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
		<%@ include file="/resources/css/button.css" %>
	</style>
	
	<%@ include file="/WEB-INF/jspf/localizationVar.jspf" %>
	
	<title><fmt:message key="title"/></title>
	
</head>
<body>
	<nav>	
		<ul>
			<li class="logo"><fmt:message key="title"/></li>
			<li><a href="BikeRentalServlet?command=get_all_user"><fmt:message key="usersLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=bikeCatalog"><fmt:message key="bikeCatalogLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=rental_points_page"><fmt:message key="rentalPointsLabel"/></a></li>
			<li><a href="BikeRentalServlet?command=all_orders_page"><fmt:message key="ordersLabel"/></a></li>
			<li style="float: right; margin-right: 50px"><a href="#"><c:out value="${user.name}"/></a>
				<ul>
					<li><a href="#" onclick="showProfile()"><fmt:message key="profileLabel"/></a></li>
					<li><a href="#" onclick="showPassword()"><fmt:message key="passwChangeLabel"/></a></li>
					<li><a href="BikeRentalServlet?command=logout"><fmt:message key="logoutLabel"/></a></li>
				</ul>
			</li>
			<li style="float: right; color: white; padding: 18px 0px 15px 10px;"><fmt:message key="adminLabel"/></li>
		</ul>
	</nav>
		<div id="calend" style="margin-top: 20px">
			<%@ include file="../../jspf/editUser.jspf" %>
		</div>

    <form action="BikeRentalServlet" method="post" id="addBikeForm">
        <div class="col-md-8" id="addBike" style="float: left" >
            <!--   ---------------------------------- bike brand ------------------------------------------ -->
            <div class="row">
                <div class="col-md-6">
                    <label><fmt:message key="brandLabel"/>*</label>
                </div>
                <div class="col-md-4">
                    <input type="text" id="brand" name="brand" class="form-control"
                           required
                           placeholder="${brandPlaceholder}"
                           oninvalid="setCustomValidity('${brandWarn}')"
                           oninput="setCustomValidity('')">
                </div>
            </div>
            <!--   ---------------------------------- model ------------------------------------------ -->
            <div class="row">
                <div class="col-md-6">
                    <label><fmt:message key="modelLabel"/> *</label>
                </div>
                <div class="col-md-4">
                    <input type="text" id="model" name="model" class="form-control" placeholder="${modelPlaceholder}"
                           required
                           oninvalid="setCustomValidity('${modelWarnLabel}')"
                           oninput="setCustomValidity('')">
                </div>
            </div>
            <!--   ---------------------------------- bike type ------------------------------------------ -->
            <div class="row">
                <div class="col-md-6">
                    <label><fmt:message key="bikeTypeLabel"/> *</label>
                </div>
                <div class="col-md-4">
                    <select	name="bikeTypeId" id="bikeTypeId" class="form-control" style="width: 330px; float: left;">
                        <option selected value="0">
                            <fmt:message key="chooseBikeTypeLabel"/>
                        </option>
                        <c:forEach var="item" items="${bikeTypesList}">
                            <option oni value="${item.id}">
                                <c:out value="${item.type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
                <!--   ---------------------------------- rental point ------------------------------------------ -->
            <div class="row">
                <div class="col-md-6">
                    <label><fmt:message key="rentalPointLabel"/>*</label>
                </div>
                <div class="col-md-5">
                    <select	name="rentalPointId"  id="rentalPointId" class="form-control" style="width: 330px; float: left;"
                               required
                               oninvalid="setCustomValidity('${countWarnLabel}')"
                               oninput="setCustomValidity('')">
                        <option selected value="0" >
                            <fmt:message key="chooseRentalPoint"/>
                        </option >
                        <c:forEach var="item" items="${rentalPointList}">
                            <option  value="${item.id}" >
                                <c:out value="${item.id}"/>
                                <c:out value="${item.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <!--   ---------------------------------- count bikes ---------------------------------------- -->
            <div class="row">
                <div class="col-md-6">
                    <label><c:out value="${countLabel}">:</c:out></label>
                </div>
                <div class="col-md-4">
                    <input
                            type="text"
                            name="bikeCount"
                            class="form-control"
                            placeholder="${pcLabel}"
                            required
                            value = '<c:out value="${bikeCount}"></c:out>'
                            pattern="[1-9]"
                            oninvalid="setCustomValidity('${countWarnLabel}')"
                            oninput="setCustomValidity('')">
                </div>
            </div>
            <!--   ---------------------------------- submit block ---------------------------------------- -->
            <div class="row">
                <div class="col-md-6"></div>
                <div class="col-md-5" align="center">
                    <input id="command" type="hidden" name="command" value="add_bike" />
                    <input type="submit" class="btn btn-primary" value="${addBikeLabel}"/>
                </div>
            </div>
        </div>
    </form>

    <script type="text/javascript" >
        <%@ include file="/resources/js1/editUser.js" %>
       <%@ include file="/resources/js1/bike.js" %>
    </script>
	<!---------------------------------------------BikeList--------------------->
    <jsp:include page="/WEB-INF/jsp/static/footer.jsp"/>
	<%@ include file="/WEB-INF/jspf/message.jspf" %>
</body>
</html>