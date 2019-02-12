<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Search Results</title>
		<meta name="google-signin-client_id" content="599314913931-ts6fsrqjt86vvrfip8d3e2q7csk28fsk.apps.googleusercontent.com">
    	<script src="https://apis.google.com/js/platform.js" async defer></script>
    	<link rel="stylesheet" type="text/css" href="SearchPage1.css" />
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
		
		<div class="blackBar" id = "bottom"></div>
		
		
	</body>
	
	
	
	
</html>