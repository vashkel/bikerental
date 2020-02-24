<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var = "language" value = "${not empty sessionScope.language ? sessionScope.language : 'ru' }" scope = "session"/>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="local"/>

<div style="background-color: #4246ff; height: 40px; width:100%; position:absolute; bottom:0; left:0;">
  <div style="font-family: 'Trebuchet'; font-size: 12px; padding-top: 11px; text-align: center; bottom: 0">
    &copy; <fmt:message key="developerName"/>
  </div>   
</div> 
 