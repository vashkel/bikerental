<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vashkel
  Date: 25.12.2019
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>users</title>
</head>
<body>
<cc:forEach var="num" items="${users}">
<p>${num}</p>
</cc:forEach>
</body>
</html>
