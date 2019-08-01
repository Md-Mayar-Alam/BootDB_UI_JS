jQuery(document).ready(function(){
	//jQuery coming from common.js
	
	//setting focus on username textbox
	$("#username").focus();
	
	jQuery("#loginSubmit").click(function(event){
		submitForm(event);
	});
	
	/*jQuery("#loginSubmit").on("click", function(event){
		submitForm();
	});*/
});

jQuery(document).ready(function($) {

	  if (window.history && window.history.pushState) {

	    //window.history.pushState('forward', null, 'window.locn.href' + './#forward');
		window.history.pushState('nohb', null, '');
		
	    $(window).on('popstate', function() {
	      alert('Back button was pressed.');
	      
	      var currentUrl= window.location.href;
	      window.location.href= currentUrl+ "?isBackButtonClicked=true";
	      
	      /*$.ajax({
	    	 type: "GET",
	    	 url: window.location.href + "&isBackButtonClicked=true",
	    	 
	      });*/
	    });

	  }
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
			crossDomain: true,
			success: function(response, data, xhr){
				var accessToken= response.accessToken;
				var refreshToken= response.refreshToken;
				
				var header1= xhr.getResponseHeader("Set-Cookie");
				var header2= xhr.getResponseHeader("ACCESS_TOKEN_COOKIE");
				var header3= xhr.getResponseHeader("test");
				
				if(accessToken != null && !isEmptyString(accessToken) && 
						refreshToken != null && !isEmptyString(refreshToken) &&
						$.isEmptyObject(Token)){
					
					Token.accessToken= accessToken;
					Token.refreshToken= refreshToken;
				}else{
					console.log("something wrong with jwt token");
				}
			},
			error: function(error){
				if(error.responseJSON.status == "UNAUTHORIZED"){
					$("#loginError").removeClass("hide");
					
					$("#username").val("");
					$("#password").val("");
					
					$("#username").focus();
				}
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
