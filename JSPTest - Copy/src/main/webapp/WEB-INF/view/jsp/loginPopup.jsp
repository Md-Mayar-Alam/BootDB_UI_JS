<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	</head>
	<body>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#loginPopupModal">
			Open Login Modal
		</button>
	
		<div id="loginPopupModal" class="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						Hello Modal
					</div>
					
					<div class="modal-body">
						Modal Body
					</div>
					
					<div class="modal-footer">
						Modal Footer
					</div>
				</div>
			</div>
		</div>
	
		<%-- <div id="loginPopup">
			<form:form modelAttribute="loginRequest">
				<label for="username">Username</label>
				<form:input path="username"/>
				
				<label for="password">Password</label>
				<form:input path="password"/>
				
				<form:button>Sign In</form:button>
			</form:form>
		</div> --%>
	</body>
</html>