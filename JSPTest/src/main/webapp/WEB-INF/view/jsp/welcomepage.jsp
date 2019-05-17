<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


<script type="text/javascript" src="js/jQuery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/welcomepage.js"></script>

<link href="css/welcomepage.css" rel="stylesheet">

<!-- JS and CSS for featherlight  -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/featherlight/1.7.7/featherlight.min.js" type="text/javascript" charset="utf-8"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/featherlight/1.7.7/featherlight.min.css" type="text/css" rel="stylesheet" /> -->

</head>
<body>
	<h1 id="helloWorld" class="hello">Hello World</h1>
	<spring:message code="welcomepage.hello.google"></spring:message>

	<div id="userLogin">
		<a href="/login"> <spring:message code="login.submit" text="Login"></spring:message>
		</a>
	</div>

</body>
</html>