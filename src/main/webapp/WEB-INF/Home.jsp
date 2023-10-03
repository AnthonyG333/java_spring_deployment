<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/Home.css" />
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> Home / Liaison </title>
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
	
		<div align="right">
			<form:form action="/liaison/public/message/post/1" method="post" modelAttribute="publicMessage" >
				<form:hidden path="user" value="${user_id}"/>
				<form:textarea path="body" cols="5" rows="5" placeholder="What's on your mind?" class="form-control input-lg"/>
				<form:errors path="body" class="text-danger"/>
				<button type="submit" class="btn btn-primary rounded m-1"> Post Public Message</button>
			</form:form> 
		</div>
		
		<div>
		<!-- PAGE NUMBERS & ALL MESSAGES -->
			<div class="m-2" >
			<!-- PAGE NUMBERS -->
				<c:forEach begin="1" end="${totalPages}" var="index">
					<h3> <a href="/liaison/dashboard/${index}" style="color:blue"> <c:out value="${index}" /> </a> </h3>
				</c:forEach>
			</div>
			
			<!-- ALL MESSAGES -->
			<c:forEach var="message" items="${publicMessages.content}">				
				<div id="messageContainer">
					
					<div id="parent">
						<div id="child1">
							<c:if test="${message.user.avi != null}" >
								<img src="/liaison/user/avi/view/<c:out value='${message.user.id}'/>" id="avi"/>
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
								<form action="/liaison/public/message/delete/${message.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
						<a href="/liaison/reply/get/${message.id}" class="btn-sm btn-success rounded">️  Replies: <c:out value="${message.popularity}"/> </a>
				 	</div>
				 	
				</div>
			</c:forEach>
			
		</div>
	</div>
		
	<div class="col p-0" id="column3">
	
		<div>
			<form action="/liaison/public/message/search" method="get" class="d-flex">
				<input type="text" name="searchQuery" placeholder="Search all messages here:" class="form-control input-lg m-2" />
				<button type="submit" class="btn-primary rounded"> Search </button>
			</form>
		</div>
		
		<div>
		<h3 style="color:blue" class="mt-3 mb-3 ml-3"> Popular Messages </h3>
			<c:forEach var="message" items="${popularMessages}">
				<div id="messageContainer">
					
					<div id="parent">
						<div id="child1">
							<c:if test="${message.user.avi != null}" >
								<img src="/liaison/user/avi/view/<c:out value='${message.user.id}'/>" id="avi"/>
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
								<form action="/liaison/public/message/delete/${message.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
						<a href="/liaison/reply/get/${message.id}" class="btn-sm btn-success rounded">️  Replies: <c:out value="${message.popularity}"/> </a>
				 	</div>
					
				</div>
			</c:forEach>
		</div>
		
	</div>
</div>
</body>
</html>