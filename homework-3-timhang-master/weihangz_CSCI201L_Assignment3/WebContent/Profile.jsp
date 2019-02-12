<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Profile Page</title>
    <meta charset="utf-8" />
    <meta name="google-signin-client_id" content="599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link rel="stylesheet" type="text/css" href="Profile1.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  </head>
  <body>
  	<div class="blackBar" id="top"></div>
	
	<div class="search-container">
  			<form id="searchBar" role= "search" action="SearchPage.jsp">
  				<input type="search" id = "search" name = "search" placeholder="Search..">
  				<button type="submit" id = "sub"><i class="fa fa-search"></i></button>
  			</form>
  	</div>
	
	<div class = "menuOptions">
		<div id = "Calendar">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment3/LoggedInScreen.jsp" class="button">Sycamore Calendar</a>
		</div>
		<div id = "Profile">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment3/Profile.jsp" class="button">Profile</a>
		</div>
		<div id = "Home">
			<a href="http://localhost:8080/weihangz_CSCI201L_Assignment3/Home.jsp" class="button">Home</a>
		</div>
		
		
	</div>
	<div class="blackBar" id = "bottom">
	</div>
	<div>
    <h2 id= "title">Upcoming Events</h2>
    <div style="overflow-y:auto;">
		<table id = "events" class="fixed_header">
  			<thead>
    			<tr>
      				<th>Date</th>
      				<th>Time</th>
      				<th>Event Summary</th>
    			</tr>
  			</thead>
  			<tr>
  			</tr>
  			<tbody>
  			</tbody>
  		</table>
	</div>
		
	</div>
	
	<div class = "parent" >
		<img id = "pic" alt="No Image" />
		<p id = "name" ></p>
	</div>
	
    <!--Add buttons to initiate auth sequence and sign out-->
    <button id="authorize_button" style="display: none;">Authorize</button>
    <!--   -->
	<button id="signout_button" style="display: none;">Sign Out</button>
    <pre id="content"></pre>
	
	
    <script type="text/javascript">
      // Client ID and API key from the Developer Console
      var CLIENT_ID = '599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com';
      var API_KEY = '';

      // Array of API discovery doc URLs for APIs used by the quickstart
      var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];

      // Authorization scopes required by the API; multiple scopes can be
      // included, separated by spaces.
      var SCOPES = "https://www.googleapis.com/auth/calendar";

      var authorizeButton = document.getElementById('authorize_button');
      var signoutButton = document.getElementById('signout_button');
	  
      /**
       *  On load, called to load the auth2 library and API client library.
       */
      function handleClientLoad() {
        gapi.load('client:auth2', initClient);
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
          gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

          // Handle the initial sign-in state.
          updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
          
          var name = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getName();
          var email = gapi.auth2.getAuthInstance().currentUser.get().getBasicProfile().getEmail();
          document.getElementById("pic").src = "https://pikmail.herokuapp.com/"+ email+ "?size=150";
          document.getElementById("name").innerHTML = name;
          authorizeButton.onclick = handleAuthClick;
          signoutButton.onclick = handleSignoutClick;
        });
      }

      /**
       *  Called when the signed in status changes, to update the UI
       *  appropriately. After a sign-in, the API is called.
       */
      function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
          authorizeButton.style.display = 'none';
          signoutButton.style.display = 'none';
          listUpcomingEvents();
        } else {
          authorizeButton.style.display = 'none';
          signoutButton.style.display = 'none';
        }
      }

      /**
       *  Sign in the user upon button click.
       */
      function handleAuthClick(event) {
        //gapi.auth2.getAuthInstance().signIn();
        //console.log(event);
      }

      /**
       *  Sign out the user upon button click.
       */
      function handleSignoutClick(event) {
        //gapi.auth2.getAuthInstance().signOut();
      }

      /**
       * Append a pre element to the body containing the given message
       * as its text node. Used to display the results of the API call.
       *
       * @param {string} message Text to be placed in pre element.
       */
      /*function appendPre(message) {
        var pre = document.getElementById('content');
        var textContent = document.createTextNode(message + '\n');
        pre.appendChild(textContent);
      }*/
      
      function appendTable(summary, date) {
    	  if(summary == "No upcoming events found."){
    		  var table = document.getElementById("events");
    		  var row = table.insertRow(1);
    		  var cell1 = row.insertCell(0);
    		  var cell2 = row.insertCell(1);
   	       	  var cell3 = row.insertCell(2);
    		  cell1.innerHTML = summary;
    	  } else {
    		  
    	  
    	  var months = ["January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	      /*console.log(months[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear());
	      if(date.getMinutes()<10){
	    	  console.log(date.getHours()+":0"+date.getMinutes());
	      }
	     	   
	       else {
	    	   console.log(date.getHours()+":"+date.getMinutes());
	       }
	       	   
	       console.log(summary);*/
	       var table = document.getElementById("events");
	       var row = table.insertRow(1);
	       var cell1 = row.insertCell(0);
	       var cell2 = row.insertCell(1);
	       var cell3 = row.insertCell(2);
	       cell1.innerHTML = months[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear();
	       if(date.getMinutes()<10){
	    	   cell2.innerHTML = date.getHours()+":0"+date.getMinutes();
	       } else {
	    	   cell2.innerHTML = date.getHours()+":"+date.getMinutes();
	       }
	       cell3.innerHTML = summary;
	       
    	  }
      }

      /**
       * Print the summary and start datetime/date of the next ten events in
       * the authorized user's calendar. If no events are found an
       * appropriate message is printed.
       */
      /*function getProfilePicture(){
    	  //var profile = googleUser.getBasicProfile();
    	  GoogleUser
      }*/
      
      function listUpcomingEvents() {
    	    gapi.client.calendar.events.list({
    	     'calendarId' : 'primary',
    	     'timeMin' : (new Date()).toISOString(),
    	     'showDeleted' : false,
    	     'singleEvents' : true,
    	     'maxResults' : 10,
    	     'orderBy' : 'startTime'
    	    }).then(function(response) {
    	     var events = response.result.items;
    	     if (events.length > 0) {
    	      for (i = events.length-1; i >=0; i--) {
    	       var event = events[i];
    	       var when = event.start.dateTime;
    	       if (!when) {
    	        when = event.start.date;
    	       }
    	       appendTable(event.summary, new Date(when));
    	       var date = new Date(when);
    	       /*var months = ["January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    	       console.log(months[date.getMonth()]+" "+date.getDate()+", "+date.getFullYear());*/
    	       
    	      }
    	     } else {
    	      appendTable("No upcoming events found.", null);
    	     }
    	    });
    	   }
      
      /*function listUpcomingEvents() {
        gapi.client.calendar.events.list({
          'calendarId': 'primary',
          'timeMin': (new Date()).toISOString(),
          'showDeleted': false,
          'singleEvents': true,
          'maxResults': 10,
          'orderBy': 'startTime'
        }).then(function(response) {
          var events = response.result.items;
          appendPre('Upcoming events:');

          if (events.length > 0) {
            for (i = 0; i < events.length; i++) {
              var event = events[i];
              var when = event.start.dateTime;
              if (!when) {
                when = event.start.date;
              }
              appendPre(event.summary + ' (' + when + ')')
            }
          } else {
            appendPre('No upcoming events found.');
          }
        });
      }*/

    </script>
	
	
	
    <script async defer src="https://apis.google.com/js/api.js"
      onload="this.onload=function(){};handleClientLoad()"
      onreadystatechange="if (this.readyState === 'complete') this.onload()">
    </script>
  </body>
</html>