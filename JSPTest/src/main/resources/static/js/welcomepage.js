$(document).ready(function(){
	$("#helloWorld").click(function(){
		changeColour();
	})
});

function changeColour(){
	var element = $("#helloWorld");
	
	element.removeClass("hello");
	element.addClass("blueColour");
}