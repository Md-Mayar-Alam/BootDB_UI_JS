
$(document).ready(function(){
	$("#helloWorld").click(function(){
		changeColour();
	});
	
	$("#userLogin").click(function(event){
		setLastAccessedUrl();
	});
});

function changeColour(){
	var element = jQuery("#helloWorld");
	
	element.removeClass("hello");
	element.addClass("blueColour");
	
	//Simple call of featherlight for opening popup
	//$.featherlight($('#mylightbox1'), {});
}

function setLastAccessedUrl(){
	lastAccessedUrl= window.location.href;	//saving lastAccessedUrl in common.js for further redirecting
											//to it after successful authentication
}


/*Code for getting popup login page using featherlight but don't know why the js on submit not working
so finally have to leave it*/
/*function getLoginPage(event){
	event.preventDefault();
	
	$.ajax({
		type: "GET",
		url: "/login",
		success: function(data){
			data.trim();
			$("#showLogin").html(data);
			$.featherlight($("#showLogin"), {});
			
			
			$.featherlight(data, {
				closeIcon:"&#10006",
				closeOnClick:"background",
				contentFilters: ['html']
			});
		},
		error: function(error){
			console.log(error);
		}
	});
}*/