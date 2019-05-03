<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<script type="text/javascript" src="js/jQuery/3.3.1/jquery.min.js"></script>
		<script type="text/javascript" src="js/login.js"></script>
		<link href="css/login.css" rel="stylesheet">
	</head>
	
	<body>
		<form:form id="loginForm" modelAttribute="loginRequest" method="POST">
			<div>
				<div>
					<label for="username">
						<spring:message code="login.username"></spring:message>
					</label>
					<form:input id="username" path="username"/>
					<form:errors id="usernameErrorBack" path="username" class="error"></form:errors>
					<span id="usernameErrorFront" class="error hide">
						<spring:message code="login.username.error.front"></spring:message>
					</span>
				</div>
				<div>
					<label for="password">
						<spring:message code="login.password"></spring:message>
					</label>
					<form:password id="password" path="password"/>
					<form:errors id="passwordErrorBack" path="password" class="error"></form:errors>
					<span id="passwordErrorFront" class="error hide">
						<spring:message code="login.password.error.front"></spring:message>
					</span>
				</div>
				<div>
					<form:button id="loginSubmit">
						<spring:message code="login.submit"></spring:message>
					</form:button>
				</div>
			</div>
		</form:form>	
		
		<%-- <form id="loginForm">
			<div>
				<div>
					<spring:message code="login.username"></spring:message>
					<input id="username" type="text"/>
					<span id="usernameErrorFront" class="error hide">
						<spring:message code="login.username.error.front"></spring:message>
					</span>
				</div>
				<div>
					<spring:message code="login.password"></spring:message>
					<input id="password" type="password"/>
					<span id="passwordErrorFront" class="error hide">
						<spring:message code="login.password.error.front"></spring:message>
					</span>
				</div>
				<div>
					<button id="loginSubmit">
						<spring:message code="login.submit"></spring:message>
					</button>
				</div>
			</div>
		</form> --%>
		
	</body>
</html>

