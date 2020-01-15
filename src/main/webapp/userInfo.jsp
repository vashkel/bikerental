<%--
  Created by IntelliJ IDEA.
  User: vashkel
  Date: 25.12.2019
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>users</title>
</head>
<body>
<form method="get" action="BikeRentalServlet">
User:<input type="text" name="id">
<input type="submit" value="sent">

</form>
<br>
<a href="http://localhost:8080/">new</a>
<a href="allusers.jsp">All Users</a>
</body>
</html>
