<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/PrivateMessage.css" />
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> Private Messages / Liaison </title>
</head>
<body>
<div align="left" class="m-4">
	<h1 style="color:black"> <c:out value="${currUser.userName}"/>'s Liaison </h1>
</div>
<div class="row" id="container">

	<div class="col" id="column1" >
		<h2 class="row" id="navItem"> <a href="/liaison/home/1" style="color:blue"> Home </a> </h2> 
		<h2 class="row" id="navItem"> <a href="/liaison/user/${user_id}" style="color:blue"> Profile </a> </h2>
		<h2 class="row" id="navItem"> <a href="/liaison/private/messages" style="color:blue"> Private Messages </a> </h2>
		<h4 class="row" id="navItem"> <a href="/liaison/logout" style="color:blue"> Logout </a> </h4>
	</div>
	
	<div class="col" id="column2" align="center">
		<h3 class="text-danger mt-4"> Select a friend to have a conversation with, or search users below. </h3>
		
		<form action="/liaison/friend/search" method="get" class="d-flex">
			<input type="text" name="searchQuery" placeholder="Search all users here:" class="form-control input-lg m-2" />
			<button type="submit" class="btn-primary rounded"> Search </button>
		</form>
		
	</div>
	
	<div class="col" id="column3" align="center">
	
		<h1 align="center" class="text-decoration"> <c:out value="${currUser.userName}"/>'s Friends </h1>
		
		<div class="col">
			<c:forEach var="friend" items="${currUser.friends}">
				<div class="row" id="friendContainer">
					<img src="/liaison/user/avi/view/<c:out value='${friend.friendId}'/>" id="avi" /> 
					<a href="/liaison/private/messages/get/${friend.friendId}" class="font-italic mt-4" id="userName" align="center"> <c:out value="${friend.userName}"/> </a>
				</div>
			</c:forEach>
		</div>
		
	</div>
</div>
</body>
</html>