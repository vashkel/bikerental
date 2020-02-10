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
        <%@include file="/resources/css/header.css"%>
    </style>

    <%@ include file="../../WEB-INF/jspf/localizationVar.jspf" %>
    <title><fmt:message key="title"/></title>
</head>
<body>

<c:set var="menuLabel" value="${rentalPointsLabel}" scope="page"/>
<%@ include file="../../WEB-INF/jspf/smallMenu.jspf" %>

<!---------------------------------------------Rental point List--------------------->
<div id="body" style="margin: 20px">
    <c:if test="${rentalPointList!=null}">
        <div style="text-align:center; margin-bottom:10px; font-size: 28px">
            <fmt:message key="rentalPointsLabel"/>
        </div>

        <div style="margin-left: 20px">
            <div class="row">
                <div class="col-md-12">
                    <table class="table">
                        <thead>
                        <tr>
                            <th><fmt:message key="rentalPointName"/></th>
                            <th><fmt:message key="rentalPointAddress"/></th>
                            <th><fmt:message key="rentalPointTel"/></th>
                        </tr>
                        </thead>
                        <c:forEach items="${rentalPointList}" var="item">
                            <tbody>
                            <tr>
                                <td><c:out value="${item.name}"/></td>
                                <td><c:out value="${item.adress}"/></td>
                                <td><c:out value="${item.tel}"/></td>
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
<jsp:include page="/jsp/static/footer.jsp"></jsp:include>
</body>
</html>