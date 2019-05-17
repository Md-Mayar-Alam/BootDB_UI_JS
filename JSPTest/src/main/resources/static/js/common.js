/**
 * contains functions and attributes which are common
 * for all throughout the website
 * @author Mayar Alam
 */

var jQuery= $;

var Token= {};

var lastAccessedUrl;

var isEmptyString= function(stringToCheck){
	if(typeof(stringToCheck) === 'string'){
		if(!stringToCheck.trim()){
			return true;
		}
		return false;
	}
}