<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/FriendSearch.css" />
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> Search Results / Liaison </title>
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
		<div class="mt-2 mb-2">
			<h3 class="text-danger mt-4"> Select a friend to have a conversation with, or search users below. </h3>
		
			<form action="/liaison/friend/search" method="get" class="d-flex">
				<input type="text" name="searchQuery" value="${searchQuery}" class="form-control input-lg m-2" />
				<button type="submit" class="btn-primary rounded"> Search </button>
			</form>
		</div>
		
		<div class="col">
			<c:forEach var="user" items="${allUsers}">
				<div id="userContainer">
					<img src="/liaison/user/avi/view/<c:out value='${user.id}'/>" class="m-2" id="avi"/> 
					<a href="/liaison/user/${user.id}" class="font-italic m-2" id="userName"> <c:out value="${user.userName}"/> </a>
					<form:form action="/liaison/friend/post" method="post" modelAttribute="friend">
						<form:hidden path="user" value="${user_id}"  />
						<form:hidden path="userName" value="${user.userName}"  />
						<form:hidden path="friendId" value="${user.id}" />
						<button type="submit" class="btn btn-warning rounded m-2"> Add friend </button>
					</form:form>
				</div>
			</c:forEach>
		</div>
		
	</div>
	
	<div class="col" id="column3" align="center">
	
		<h1 align="center" class="text-decoration"> <c:out value="${currUser.userName}"/>'s Friends </h1>
		
		<div class="col">
			<c:forEach var="friend" items="${currUser.friends}">
				<c:if test="${friend.id != currFriend.id}">
					<div class="row" id="friendContainer">
						<img src="/liaison/user/avi/view/<c:out value='${friend.friendId}'/>" id="avi" /> 
						<a href="/liaison/private/messages/get/${friend.friendId}" class="font-italic mt-4" id="userName" align="center"> <c:out value="${friend.userName}"/> </a>
					</div>
				</c:if>
			</c:forEach>
		</div>
		
	</div>
</div>
</body>
</html>