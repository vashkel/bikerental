<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local"/>
<!DOCTYPE html>
<html lang="${local}">
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
<input type="hidden" name="isLoginElement" value="${loginMenu}" id="isLoginElement"/>

<nav class="navbar bg-primary navbar-dark">

	<h1 class="text-white"><fmt:message key="title"/></h1>

	<ul class="nav nav-pills pull-xs-right">
		<li class="nav-item active">
			<a href="#"
			   id="choose_type"
			   class="nav-link text-white"
			   onclick="showFunc()">
				${registrationLabel}
			</a>
		</li>
		<li class="nav-item active">
		<a class="nav-link text-white"
		   href="BikeRentalServlet?command=change_localization&local=ru&loginMenu="
		   onclick="location.href=this.href+isLogin;return false;">
			${ru_button}
		</a>
		</li>
		<li class="nav-item active">
			<a class="nav-link text-white"
			   href="BikeRentalServlet?command=change_localization&local=en&loginMenu="
			   onclick="location.href=this.href+isLogin;return false;">
				${en_button}
			</a>
		</li>
	</ul>
</nav>

<br/>
<br/>

<div id="authorization" class="container" style="display:none">

	<div style="text-align:center; font-size: 20px">
		<fmt:message key="loginFieldLabel"/>
	</div>
	<br>

	<form action="BikeRentalServlet" method="post">
		<input type="hidden" name="command" value="login"/>
		<div class="row">
			<div class="col-md-4">
				<label><fmt:message key="loginLabel"/></label>
			</div>

			<div class="col-md-4">
				<input type="text"
					   name="login"
					   class="form-control"
					   placeholder="<fmt:message key="loginPlaceholder"/>"
					   pattern="[a-zA-Z]{1}[a-zA-Z0-9]{2,20}"
					   value="${login}" required
					   oninvalid="setCustomValidity('${loginWarn}')"
					   oninput="setCustomValidity('')">
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<label><fmt:message key="passwordLabel"/></label>
			</div>

			<div class="col-md-4">
				<input type="password"
					   name="password"
					   class="form-control"
					   placeholder="<fmt:message key="passwordPlaceholder"/>"
					   required
					   pattern="^[^ ]{8,20}$"
					   oninvalid="setCustomValidity ('${password_warn}')"
					   oninput="setCustomValidity('')">
			</div>
		</div>

		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4" align="center">
				<input type="submit" class="btn btn-primary" value="${enterButton}">
			</div>
		</div>
	</form>
</div>


<div id="calend">
	<div id="registration" class="container" style="display:none">
		<div class="container">

			<div style="text-align:center; font-size: 20px">
				<fmt:message key="registerFieldLabel"/>
			</div>
			<br>

			<form action="BikeRentalServlet" method="post" id="userDataForm" onsubmit="checkUserData(); return false">

				<%@ include file="/WEB-INF/jspf/userPersonalData.jspf" %>

				<%@ include file="/WEB-INF/jspf/password.jspf" %>

				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4" align="center">
						<input type="hidden" name="command" value="register"/>
						<input type="hidden" name="isLogin" value="false"/>
						<input type="submit" class="btn btn-primary" value="${registrationButton}">
					</div>
				</div>
			</form>

		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/message.jspf" %>

<script type="text/javascript">
    var isLogin = initLogin();
    <%@include file="/resources/js1/login.js"%>
</script>
<jsp:include page="/jsp/static/footer.jsp"/>
</body>
</html>