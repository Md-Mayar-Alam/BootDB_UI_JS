<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link href="css/welcomepage.css" rel="stylesheet">
<script type="text/javascript" src="js/jQuery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/welcomepage.js"></script>
</head>
<body>
	<h1 id="helloWorld" class="hello">Hello World</h1>
	<spring:message code="welcomepage.hello.google"></spring:message>
</body>
</html>