$(document).ready(function(){
	/*$("#loginSubmit").click(function(event){
		submitForm(event);
	});*/
	
	$("#loginSubmit").on("click", function(event){
		submitForm(event);
	});
});

function submitForm(event){
	event.preventDefault();
	
	var formId= "loginForm";
	
	var isFormOK= checkFormData(formId);
	
	if(isFormOK == true){
		
		var formData= {
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		$.ajax({
			url: "/api/auth/login",
			type: "POST",
			contentType: "application/json; charset=utf-8",		/*contentType is the header sent to the server, specifying a particular format*/
			data: JSON.stringify(formData),
			success: function(response, data){
				var accessToken= response.accessToken;
				var refreshToken= response.refreshToken;
			},
			error: function(error){
				console.log(error);
			}
		});
		
		/*
		 * The JSON.stringify method converts a JavaScript value into a JSON string
		 * var book = {
				    title: 'JavaScript: The Definitive Guide',
				    author: 'David Flanagan',
				    edition: 6
				};
				
			var json = JSON.stringify(book);
			console.log(json);
			// {"title":"JavaScript: The Definitive Guide","author":"David Flanagan","edition":6}
		 */
		
	}else{
		return;
	}
}

function checkFormData(formId){
	
	clearErrorsForForm(formId);
	
	var username= $("#username").val();
	var password= $("#password").val();
	
	var isValid= true;
	
	if(username == null || username==''){
		$("#usernameErrorFront").removeClass("hide");
		isValid= false;
	}
	
	if(password == null || password==''){
		$("#passwordErrorFront").removeClass("hide");
		isValid= false;
	}
	
	return isValid;
}

function clearErrorsForForm(formId){
	var form= $("#" + formId);
	
	$.each($("[id*=Error]", form), function(){
		$(this).addClass("hide");
	});
}