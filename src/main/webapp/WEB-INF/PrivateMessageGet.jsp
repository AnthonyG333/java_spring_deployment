<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/PrivateMessageGet.css" />
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
	
	<div class="col p-0" id="column2">
	
		<div id="header">
			<h2> <a href="/liaison/user/${currFriend.id}" class="font-italic" id="userName"> <c:out value="${currFriend.userName}"/> </a> </h2>
			<img src="/liaison/user/avi/view/<c:out value='${currFriend.id}'/>" class="m-4" id="avi1"/> 
		</div>
		
		<div align="right">
			<form:form action="/liaison/private/message/post" method="post" modelAttribute="privateMessage" class="mb-4">
				<form:hidden path="user" value="${currUser.id}"/>
				<form:hidden path="recipientId" value="${currFriend.id}"/>
				<form:hidden path="recipientUserName" value="${currFriend.userName}"/>
				<form:textarea path="body" placeholder="What do you want to say to ${currFriend.userName}?" class="form-control input-lg"/>
				<form:errors path="body" class="text-danger"/>
				<button type="submit"  class="btn btn-primary rounded"> Post Private Message</button>
			</form:form>
		</div> 
		
		<div>
			<c:forEach var="message" items="${allMessages}">
			
			<c:choose>
				<c:when test="${message.user.id == user_id}">
				
					<div id="messageContainer">
						<div id="parent">
							<div id="child1">
								<c:if test="${message.user.avi != null}" >
									<img src="/liaison/user/avi/view/<c:out value='${message.user.id}'/>" id="avi2"/>
								</c:if>
							</div>
						
							<div id="child2">
								<div id="baby">
									<p> <a href="/liaison/user/${message.user.id}"> <c:out value="${message.user.userName}"/> </a> </p>
									<p id="date"> <fmt:formatDate value="${message.createdAt}" /> </p>
								</div>
								<div>
									<p class="text-wrap"> <c:out value="${message.body}"/> </p>
								</div>
							</div>
						</div>
						
						<div id="buttons">
							<c:if test="${message.user.id == user_id}" > 								
								<form action="/liaison/private/message/delete/${message.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
				 		</div>
				 	</div>
				 	
				</c:when>
				<c:otherwise>
				 	
				 	<div id="messageContainer">
						<div id="parent">
							<div id="child22">
								<div id="baby2">
									<p> <a href="/liaison/user/${message.user.id}"> <c:out value="${message.user.userName}"/> </a> </p>
									<p id="date2"> <fmt:formatDate value="${message.createdAt}" /> </p>
								</div>
								<div align="right">
									<p class="text-wrap"> <c:out value="${message.body}"/> </p>
								</div>
							</div>
							<div id="child12">
								<c:if test="${message.user.avi != null}" >
									<img src="/liaison/user/avi/view/<c:out value='${message.user.id}'/>" id="avi2"/>
								</c:if>
							</div>
				 		</div>
				 	
				 		<div id="buttons2">
							<c:if test="${message.user.id == user_id}" > 								
								<form action="/liaison/public/message/delete/${message.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
				 		</div>
				 	</div>
				 	
				</c:otherwise>
			</c:choose>
				
			</c:forEach>
		</div>
		
	</div>
	
	<div class="col" id="column3" align="center">
	
		<h1 align="center" class="text-decoration"> <c:out value="${currUser.userName}"/>'s Friends </h1>
		
		<div class="col">
			<c:forEach var="friend" items="${currUser.friends}">
				<div class="row" id="friendContainer">
					<img src="/liaison/user/avi/view/<c:out value='${friend.friendId}'/>" id="avi3" /> 
					<a href="/liaison/private/messages/get/${friend.friendId}" class="font-italic mt-4" id="userName" align="center"> <c:out value="${friend.userName}"/> </a>
				</div>
			</c:forEach>
		</div>
		
	</div>
	
</div>
</body>
</html>