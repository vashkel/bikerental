<html>
<head>
    <title>Index 2</title>
</head>
<body>
<jsp:useBean id="calendar" scope="page" class="java.util.GregorianCalendar"/>
Directive
<%@include file="result.jsp"%>
<br/>
Action tag
<jsp:include page="result.jsp"></jsp:include>
<jsp:useBean id="ob" scope="page" class="java.util.Random"/>
<jsp:setProperty name="ob" property="seed"/>
${ob.doubles()}
<h1>Heeeeloooo123e33  </h1>
</body>
</html>