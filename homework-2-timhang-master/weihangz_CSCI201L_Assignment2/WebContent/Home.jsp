<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Logged-in Home Page</title>
    <meta name="google-signin-scope" content="profile email">
    
    
    <meta name="google-signin-client_id" content="599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link rel="stylesheet" type="text/css" href="Home.css" />
  </head>
  <body>
  	<div class="blackBar" id="top"></div>
	
	
	<div class = "menuOptions">
		<div id = "Calendar">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment2/LoggedInScreen.jsp" class="button">Sycamore Calendar</a>
		</div>
		<div id = "Profile">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment2/Profile.jsp" class="button">Profile</a>
		</div>
		<div id = "Home">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment2/Home.jsp" class="button">Home</a>
		</div>
		
		
	</div>
	<div class = "hometop">
		<h2 id = "title">Home</h2>
	</div>
  	
   
   <div class="boxed">
  		<img id = "pic" alt="No Image"  />
		<p id = "name" ></p>
		
		<div class="boxed1">
		<form id = "myForm">
  			<input type = "text" name = "eventTitle" id = "eventTitle" placeholder = "Event Title" required />
  			<br/>
  			<input type="text" class = "textbox-n" id = "startDate" onfocus="(this.type='date')" placeholder = "Start Date" required/>
  			<input type="text" class = "textbox-n" id = "endDate" onfocus="(this.type='date')" placeholder = "End Date" required/>
  			<input type="button" value="Add Event" onclick="createEvents();"/>
  			<br/>
  			
  			<input type="text" class = "textbox-n" id = "startTime" onfocus="(this.type='time')" placeholder = "Start Time" required/>
  			<input type="text" class = "textbox-n" id = "endTime" onfocus="(this.type='time')" placeholder = "End Time" required/>
		</form>
		</div>
		
		
   </div>
    
    
    <div class="blackBar" id = "bottom"></div>
    
    
    
    <script>
    
	function createEvents() {
		/*document.getElementById("myForm").onsubmit = function () {
		    if (!document.getElementById("eventTitle").value) {
		        return false;
		    }
		}*/
		
      	var summary   =  document.getElementById("eventTitle").value;
		if(summary ==null || summary==""){
			alert("Please Fill Event Title!");
        	return false;}
      	var startDate =  document.getElementById("startDate").value;
      	if(startDate ==null || startDate==""){
			alert("Please Fill Start Date!");
        	return false;}
      	var endDate   =  document.getElementById("endDate").value;
      	if(endDate ==null || endDate==""){
			alert("Please Fill End Date!");
        	return false;}
      	var startTime =  document.getElementById("startTime").value;
    	if(startTime ==null || startTime==""){
			alert("Please Fill Start Time!");
        	return false;}
      	var endTime   =  document.getElementById("endTime").value;
      	if(endTime ==null || endTime==""){
			alert("Please Fill End Time!");
        	return false;}
      	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
      	var startDateTime = startDate + 'T'+ startTime +':00-07:00';
      	var endDateTime   = endDate   + 'T'+ endTime   +':00-07:00';
      	if (startDateTime>endDateTime){
      		alert("Start date time has to be earlier than end date time!");
      		return false;
      	}
      	
      	//console.log(startDateTime);
      	var event = {
      		  'summary': summary,
      		  'start': {
      		    'dateTime': startDateTime,
      		    'timeZone': 'America/Los_Angeles'
      		  },
      		  'end': {
      		    'dateTime': endDateTime,
      		    'timeZone': 'America/Los_Angeles'
      		  },
      		};
      	
      	var request = gapi.client.calendar.events.insert({
      	  'calendarId': 'primary',
      	  'resource': event
      	});

      	request.execute(function(event) {
      	  //appendPre('Event created: ' + event.htmlLink);
      		alert("Event Successfully Added!");
      		window.location.reload();
      	});
      	//console.log(summary+" "+startDate+" "+endDate+ " "+ startTime+ " "+endTime);
      	
    	    /*gapi.client.calendar.events.insert({
    	     'calendarId' : 'primary',
    	     'timeMin' : (new Date()).toISOString(),
    	     'showDeleted' : false,
    	     'singleEvents' : true,
    	     'maxResults' : 10,
    	     'orderBy' : 'startTime'
    	    }).then(function(response) {
    	     
    	    });*/
    	   }
 	// Client ID and API key from the Developer Console
    var CLIENT_ID = '599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com';
    var API_KEY = '';

    // Array of API discovery doc URLs for APIs used by the quickstart
    var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];

    // Authorization scopes required by the API; multiple scopes can be
    // included, separated by spaces.
    var SCOPES = "https://www.googleapis.com/auth/calendar";
    
    /**
     *  On load, called to load the auth2 library and API client library.
     */
    function handleClientLoad() {
        gapi.load('client:auth2', initClient);
        console.log("loaded");
      }
    /**
     *  Initializes the API client library and sets up sign-in state
     *  listeners.
     */
    function initClient() {
      gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
      }).then(function () {
        // Listen for sign-in state changes.
        
        
        var name = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getName();
        var email = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail();
        document.getElementById("pic").src = "https://pikmail.herokuapp.com/"+ email+ "?size=150";
        document.getElementById("name").innerHTML = name;
        
      });
      
      
    }
    
    </script>
    
    
    
    
	<script async defer src="https://apis.google.com/js/api.js"
      onload="this.onload=function(){};handleClientLoad()">
     </script>
  </body>
  
</html>