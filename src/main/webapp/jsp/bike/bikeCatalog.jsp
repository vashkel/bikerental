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
        <%@include file="/resources/css/commonStyles.css"%>
        <%@include file="/resources/css/header.css"%>
    </style>

    <%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>

    <title><fmt:message key="title"/></title>

</head>
<body>
    <c:set var="menuLabel" value="${bikeCatalogLabel}" scope="page"/>
    <%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>
            <!---------------------------------------------BikeList--------------------->

            <div id="body" style="margin: 20px" >
                <c:if test="${bikeList!=null}">
                    <div style="text-align:center; margin-bottom:10px; font-size: 28px">
                        <fmt:message key="bikesLabel"/>
                    </div>
                    <div style="margin-left: 20px">
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="bikeNubmerLabel"/></th>
                                        <th><fmt:message key="brandLabel"/></th>
                                        <th><fmt:message key="modelLabel"/></th>
                                        <th><fmt:message key="bikeTypeLabel"/></th>
                                        <th><fmt:message key="parkingLabel"/></th>
                                        <th><fmt:message key="statusBikeLabel"/></th>
                                        <th><fmt:message key="changes"/></th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${bikeList}" var="item">
                                        <tbody>
                                        <tr>
                                            <td><c:out value="${item.id}"/></td>
                                            <td><c:out value="${item.brand}"/></td>
                                            <td><c:out value="${item.model}"/></td>
                                            <td><c:out value="${item.bikeType.type}"/></td>
                                            <td><c:out value="${item.rentalPoint.id}"/>
                                                <c:out value="${item.rentalPoint.name}"/>
                                                <c:out value="${item.rentalPoint.adress}"/>
                                            </td>
                                            <td><c:out value="${item.bikeStatus}"/></td>
                                            <td>
                                                <div class="dropdown">
                                                <button onclick="myFunction()" class="dropbtn"><fmt:message key="change"/></button>
                                                <div id="myDropdown" class="dropdown-content">
                                                    <a href="BikeRentalServlet?command=delete_bike&bikeId=${item.id}">
                                                        <fmt:message key="deleteLabel"/></a>
                                                    <a href="BikeRentalServlet?command=update_bike&bikeId=${item.id}">
                                                        <fmt:message key="updateLabel"/></a>
                                                </div>
                                            </div>
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
            <%@ include file="/WEB-INF/jspf/pagination.jspf" %>
    <%@ include file="../../WEB-INF/jspf/message.jspf" %>

</body>
</html>