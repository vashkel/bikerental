
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
<%@ include file="../../jspf/smallMenu.jspf" %>
<div id="calend" style="margin-top: 20px">
    <%@ include file="../../jspf/editUser.jspf" %>
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
                            <th><fmt:message key="userIdLabel"/></th>
                            <th><fmt:message key="loginLabel"/></th>
                            <th><fmt:message key="nameLabel"/></th>
                            <th><fmt:message key="surnameLabel"/></th>
                            <th><fmt:message key="emailLabel"/></th>
                            <th><fmt:message key="phoneLabel"/></th>
                            <th><fmt:message key="roleLabel"/></th>
                            <th><fmt:message key="stateLabel"/></th>
                            <th><fmt:message key="changes"/></th>
                        </tr>
                        </thead>
                        <c:forEach items="${usersList}" var="item">
                            <tbody>
                            <tr>
                                <td><c:out value="${item.id}"/></td>
                                <td><c:out value="${item.login}"/></td>
                                <td><c:out value="${item.name}"/></td>
                                <td><c:out value="${item.surname}"/></td>
                                <td><c:out value="${item.email}"/></td>
                                <td><c:out value="${item.tel}"/></td>
                                <td><c:out value="${item.role}"/></td>
                                <td><c:out value="${item.state}"/></td>
                                <td>
                                    <a href="BikeRentalServlet?command=delete_user&userId=${item.id}">
                                        <fmt:message key="deleteLabel"/>
                                    </a> <br>
                                    <c:if test="${item.state eq 'ACTIVE'}">
                                        <a  href = "<c:url value="BikeRentalServlet?command=change_state_user&userId=${item.id}&state=blocked"> </c:url>">
                                            <fmt:message key="changeStateLabel"/>
                                        </a>
                                    </c:if>

                                    <c:if test="${item.state eq 'BLOCKED'}">
                                        <a href = "<c:url value="BikeRentalServlet?command=change_state_user&userId=${item.id}&state=active"> </c:url>">
                                            <fmt:message key="changeStateLabel"/>
                                        </a>
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

<!---------------------------------------------BikeList--------------------->
<jsp:include page="/WEB-INF/jsp/static/footer.jsp"/>
<%@ include file="/WEB-INF/jspf/message.jspf" %>
</body>
</html>