<%@ page contentType="text/html; charset=UTF-8"
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

<%@ include file="../../jspf/smallMenu.jspf" %>
<div class="container-fluid" style="padding-left: 15px; padding-right: 15px">
    <div class="row">
        <div class="col-sm-12" style="background: #F0FFF0; padding-left: 10px; padding-bottom: 50px">
            <form action="BikeRentalServlet" method="post">
                <!-- --------------------------------select rental point ------------------------------------ -->
                <div>
                    <label style="margin-left:10px; margin-top: 20px">
                        <fmt:message key="rentalPointLabel"/>:
                    </label>
                    <select name="rentalPointId" class="form-control">
                        <option selected value="0">
                            <fmt:message key="chooseRentalPoint"/>
                        </option>
                        <c:forEach items="${rentalPointList}" var="rentalPoint">
                            <option value="${rentalPoint.id}">
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
                    <select name="bikeTypeId" class="form-control">
                        <option selected value="0">
                            <fmt:message key="chooseBikeTypeLabel"/>
                        </option>
                        <c:forEach items="${bikeTypesList}" var="item">
                            <option value="${item.id}">
                                <c:out value="${item.type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <!-- --------------------------------select number days ------------------------------------ -->
                <div class="form-group">
                    <label style="text-align:center; margin-top: 20px">
                        <fmt:message key="numberDaysLabel"/>:
                    </label>
                    <input type="number" class="form-control" min="1" max="9" name="days" value=""
                           placeholder="<fmt:message key="numberOfDaysPlaceHolder"/>"
                           required
                           pattern="[1-9]"
                           oninvalid="setCustomValidity('${countWarnLabel}')"
                           oninput="setCustomValidity('')">
                </div>
                <div style="text-align: center; margin-top: 25px">
                    <input type="hidden" name="command" value="get_price_bike"/>
                    <input type="submit" class="btn btn-primary" value="<fmt:message key="priceLabel"/> "/>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${totalPrice ne null}">
    <form action="BikeRentalServlet" method="post">
        <div class="container-fluid" style="padding-left: 15px; padding-right: 15px">
            <div class="row"
                 style="font-size: 20px; font-weight: bold; padding-top: 5px; padding-left: 5px; color: #000080	">
                <div class="col-sm-12" style="background:#F0FFF0">
                    <div class="col-sm-12" style="padding-top:10px;  background: white">
                        <label style="font-size: 16px;">
                            <fmt:message key="priceByDays"/>
                            <c:out value="${totalPrice}"/>
                        </label>
                    </div>

                    <div style="text-align:center; margin-top: 25px">
                        <input type="hidden" name="command" value="order_bike"/>
                        <input type="hidden" name="totalPrice" value="${totalPrice}"/>
                        <input type="submit" class="btn btn-primary" value="<fmt:message key="orderLabel"/> ">
                    </div>
                </div>
            </div>
        </div>
    </form>
</c:if>
<%@ include file="/WEB-INF/jspf/message.jspf" %>
</body>
</html>
