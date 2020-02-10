<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local"/>
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
<c:set var="menuLabel" value="${ordersLabel}" scope="page"/>
<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>

<div id="body" style="margin: 20px">
    <c:if test="${orderList!=null}">
        <div style="text-align:center; margin-bottom:10px; font-size: 28px">
            <fmt:message key="ordersLabel"/>
        </div>
        <div style="margin-left: 20px">
            <div class="row">
                <div class="col-md-12">
                    <table class="table">
                        <thead>
                        <tr>
                            <th><fmt:message key="orderNumberLabel"/></th>
                            <th><fmt:message key="orderStartDate"/></th>
                            <th><fmt:message key="orderFinishDate"/></th>
                            <th><fmt:message key="userLabel"/></th>
                            <th><fmt:message key="bikeLabel"/></th>
                            <th><fmt:message key="bikePriceLabel"/></th>
                            <th><fmt:message key="rentalPointLabel"/></th>
                        </tr>
                        </thead>
                        <c:forEach items="${orderList}" var="item">
                            <tbody>
                            <tr>
                                <td><c:out value="${item.id}"/></td>
                                <td><c:out value="${item.startDate}"/></td>
                                <td><c:out value="${item.end_Date}"/></td>
                                <td><c:out value="${item.user.name}"/></td>
                                <td><c:out value="${item.bike.bikeType.type}"/></td>
                                <td><c:out value="${item.sum}"/></td>
                                <td><c:out value="${item.bike.rentalPoint.name}"/></td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</div>
<!-- ------------------------ end of bike order list ------------------------------ -->

</body>
</html>
