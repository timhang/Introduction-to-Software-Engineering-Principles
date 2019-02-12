<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
  	<title>Logged-in Home Page</title>
    <meta name="google-signin-scope" content="profile email">
    
    
    <meta name="google-signin-client_id" content="599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <link rel="stylesheet" type="text/css" href="http://localhost:8080/weihangz_CSCI201L_Assignment3/LoggedInScreen.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  </head>
  <body>
  
  	<div class="blackBar" id="top"></div>
  	
  	
  	
	<div>
		<img src="sycamore.png" id ="image">
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
		
		<div class="search-container">
  			<form id="searchBar" role= "search" action="SearchPage.jsp">
  				<input type="search" id = "search" name = "search" placeholder="Search..">
  				<button type="submit" id = "sub"><i class="fa fa-search"></i></button>
  			</form>
  		</div>
  		
  		<h1>Sycamore Calendar</h1>
    	<div class="g-signin2" onclick="signOut();" data-theme="white"></div>
    </div>
    
    <script>
    
    	
    function signOut() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
          console.log('User signed out.');
          location.href = "http://localhost:8080/weihangz_CSCI201L_Assignment3/SignInPage.jsp"
        });
        
      }
      
    </script>
    
    
    <div class="blackBar" id = "bottom">
	</div>
  </body>
  
</html>