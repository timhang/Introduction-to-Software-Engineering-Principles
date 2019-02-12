<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Sign-In Page</title>
    <meta name="google-signin-scope" content="profile email">
    
    
    <meta name="google-signin-client_id" content="599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link rel="stylesheet" type="text/css" href="SignInPage.css" />
  </head>
  <body>
  	<div class="blackBar" id="top"></div>
	<div>
		<img src="sycamore.png" id ="image">
	
  		<h1>Sycamore Calendar</h1>
    
    
   		<div class="g-signin2" data-onsuccess="onSignIn" data-theme="white"></div>
    </div>
    <script>
      function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        let GoogleAuth = gapi.auth2.getAuthInstance();
        // Retrieve the GoogleUser object for the current user.
        var GoogleUser = GoogleAuth.currentUser.get();
        GoogleUser.grant({'scope':'https://www.googleapis.com/auth/calendar'})
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());

        location.href = "http://localhost:8080/weihangz_CSCI201L_Assignment3/LoggedInScreen.jsp"
        if(googleUser.hasGrantedScopes('https://www.googleapis.com/auth/calendar'))
        {
            console.log("we have already been granted the Calendar scope!")
        }
        else
        {
           googleUser.grant({'scope':'https://www.googleapis.com/auth/calendar'});
        }
         // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
      };
      
		
	
      
    </script>
   
    
    <div class="blackBar" id = "bottom">
	</div>
  </body>
  
</html>