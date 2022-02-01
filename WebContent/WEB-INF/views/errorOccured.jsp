<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>BCI Tissue Bank</title>

  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  
  <script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/index.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>"/>  
  <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>"/>  

</head>
<body onload="reloadData()">
    <%@ include file="header.jsp" %>
    <div class="text-center">
    	<br><br>
		<h4>${msg}</h4>
		<a style="font-size: 20px;" href="login">Login</a>
	</div>
</body>
</html>