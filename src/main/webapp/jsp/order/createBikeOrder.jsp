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

    <%@ include file="/WEB-INF/jspf/localizationVar.jspf" %>

    <title><fmt:message key="title"/></title>

</head>
<body>

<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>



<div class="container-fluid" style="padding-left: 15px; padding-right: 15px">
    <div class="row">
        <div class="col-sm-12" style="background: #F0FFF0; padding-left: 10px; padding-bottom: 50px">
            <form action="BikeRentalServlet" method="post" >
                <!-- --------------------------------select rental point ------------------------------------ -->
                <div >
                    <label style="margin-left:10px; margin-top: 20px">
                        <fmt:message key="rentalPointLabel"/>:
                    </label>
                    <select name="rentalPointId" class="form-control">
                        <c:forEach items="${rentalPointList}" var="rentalPoint">
                            <option value="${rentalPoint.id}" >
                                <c:out value="${rentalPoint.adress}"/>
                            </option>
                        </c:forEach>

                    </select>
                </div>
                <!-- --------------------------------select bikeType ------------------------------------ -->
                <div>
                    <label style="margin-left:10px; margin-top: 20px">
                        <fmt:message key="bikeTypeLabel"/>:
                    </label>
                    <select	name="bikeTypeId"  class="form-control">
                        <c:forEach items="${bikeTypesList}" var="item">
                            <option value="${item.id}">
                                <c:out value="${item.type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <!-- --------------------------------select number days ------------------------------------ -->
                <div class="form-group">
                    <label style="margin-left:10px; margin-top: 20px">
                        <fmt:message key="numberDaysLabel"/>:
                    </label>
                    <input type="number" class="form-control" min="1" max="14" name="days" value=""/>
                </div>
                <div style="text-align: center; margin-top: 25px">
                    <input type="hidden" name="command" value="get_price_bike" />
                    <input type="submit" class="btn btn-primary" value="<fmt:message key="priceLabel"/> "/>
                </div>

            </form>
        </div>
    </div>
</div>

<c:if test="${totalPrice ne null}">
<form action="BikeRentalServlet" method="post">
    <div class="col-sm-12" style="background:#F0FFF0">
        <div class="row" style="font-size: 20px; font-weight: bold; padding-top: 5px; padding-left: 5px; color: #000080	">
            <div class="col-sm-12" style="padding-top:10px;  background: white">
                <label style="font-size: 16px;">
                    <fmt:message key="priceByDays"/>
                    <c:out value="${totalPrice}"/>
                </label>
            </div>

            <div style="text-align:center; margin-top: 25px">
                <input type="hidden" name="command" value="order_bike" />
                <input type="hidden" name="totalPrice" value="${totalPrice}" />
                <input type="submit" class="btn btn-primary"   value="<fmt:message key="orderLabel"/> ">
            </div>
        </div>
    </div>
</form>
</c:if>
        <!-- --------------------------------search results ------------------------------------ -->
        <%--<c:if test="${bikeProductList!=null}">--%>
            <%--<div class="col-md-10" style="background: #F5F5DC">--%>
                <%--<div class="row" style="font-size: 20px; font-weight: bold; padding-top: 5px; padding-left: 5px; color: #000080	">--%>
                    <%--<div class="col-md-1" style="padding-top:10px;  background: white">--%>
                        <%--<label style="font-size: 16px;"><c:out value="${bikeNubmerLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-3" style="padding-top:15px; padding-left: 40px;  background: white">--%>
                        <%--<label style="font-size: 16px;"><c:out value="${pictureLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-3" style="padding-top:15px; padding-left: 40px;  background: white">--%>
                        <%--<label style="font-size: 16px;"><c:out value="${characteristicsLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-2" style="padding-top:15px;  background: white">--%>
                        <%--<label style="font-size: 16px;"><c:out value="${parkingLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-1" style="padding-top:10px;  background: white">--%>
                        <%--<label style="font-size: 16px;" ><c:out value="${amountOfCollateralLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<div class="col-md-1" style="background: white">--%>
                        <%--<label style="font-size: 16px;"><c:out value="${rentCostPerMinuteLabel}"></c:out></label>--%>
                    <%--</div>--%>
                    <%--<c:if test="${bikeProductCatalogWithChoise}">--%>
                        <%--<div class="col-md-1" style="padding-top:10px; background: white">--%>
                            <%--<label style="font-size: 16px;"><c:out value="${makeCoiceLabel}"></c:out></label>--%>
                        <%--</div>--%>
                    <%--</c:if>--%>
                <%--</div>--%>

                <%--<c:forEach items="${bikeProductList}" var="item">--%>
                    <%--<div class="row" style="padding-top: 5px; padding-left: 5px">--%>
                        <%--<div class="col-md-1"  style="padding-top: 5px; background: white">--%>
                            <%--<div>--%>
                                <%--<c:out value="${item.id}"></c:out>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-3" style="background: white">--%>
                            <%--<c:if test="${item.bike.picturePath!=null}">--%>
                                <%--<img src="${pageContext.request.contextPath}/images/bikes/${item.bike.picturePath}"--%>
                                     <%--alt="${item.bike.model}" style= "height: 152px; border: none">--%>
                            <%--</c:if>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-3"  style="background: white">--%>
                            <%--<div>--%>
                                <%--<label style="font-size: 20px; font-weight: bold;">${item.bike.brand.brand} ${item.bike.model}</label>--%>
                            <%--</div>--%>
                            <%--<div>--%>
                                <%--<c:out value="${item.bike.bikeType.bikeType}"></c:out>,--%>
                                <%--<c:out value="${wheelSizeLabel}"></c:out>: <c:out value="${item.bike.wheelSize}"></c:out>,--%>
                                <%--<c:out value="${speedCountLabel}"></c:out>:  <c:out value="${item.bike.speedCount}"></c:out>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-2"  style="padding-top: 5px; background: white">--%>
                            <%--<div>--%>
                                <%--<c:out value="${item.parking.address}"></c:out>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-1"  style="padding-top: 5px; background: white">--%>
                            <%--<div>--%>
                                <%--<c:out value="${item.value}"></c:out>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div class="col-md-1"  style="padding-top: 5px; background: white">--%>
                            <%--<div>--%>
                                <%--<c:out value="${item.rentPrice}"></c:out>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<c:if test="${bikeProductCatalogWithChoise}">--%>
                            <%--<div class="col-md-1"  style="padding-top: 5px; background: white">--%>
                                <%--<div>--%>
                                    <%--<label><a href="FrontController?command=choose_bike_product&bikeProductId=${item.id}">${chooseLabel}</a></label>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</c:if>--%>
                    <%--</div>--%>

                <%--</c:forEach>--%>

                <%--<%@ include file="../../WEB-INF/jspf/pagination.jspf" %>--%>

            <%--</div>--%>
        <%--</c:if>--%>



<%@ include file="/WEB-INF/jspf/message.jspf" %>

</body>

</html>
