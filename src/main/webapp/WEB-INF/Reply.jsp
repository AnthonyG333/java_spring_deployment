<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/Reply.css" />
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> Replies / Liaison </title>
</head>
<body>
<div align="left" class="m-4">
	<h1 style="color:black"> <c:out value="${currUser.userName}"/>'s Liaison </h1>
</div>
<div class="row" id="container">

	<div class="col" id="column1"> 
		<h2 class="row" id="navItem"> <a href="/liaison/home/1" style="color:blue"> Home </a> </h2> 
		<h2 class="row" id="navItem"> <a href="/liaison/user/${user_id}" style="color:blue"> Profile </a> </h2>
		<h2 class="row" id="navItem"> <a href="/liaison/private/messages" style="color:blue"> Private Messages </a> </h2>
		<h4 class="row" id="navItem"> <a href="/liaison/logout" style="color:blue"> Logout </a> </h4>
	</div>
	
	<div class="col p-0" id="column2">
	
		<!-- CURR MESSAGE -->
		<div id="messageContainer1">
					
			<div id="parent">
				<div id="child1">
					<c:if test="${currMessage.user.avi != null}" >
						<img src="/liaison/user/avi/view/<c:out value='${currMessage.user.id}'/>" id="avi1"/>
					</c:if>
				</div>
						
				<div id="child2">
					<div id="baby">
						<h3 id="userName"> <a href="/liaison/user/${currMessage.user.id}"> <c:out value="${currMessage.user.userName}"/> </a> </h3>
						<h5 id="date"> <fmt:formatDate value="${currMessage.createdAt}" /> </h5>
					</div>
					<div>
						<h4 class="text-wrap"> <c:out value="${currMessage.body}"/> </h4>
					</div>
				</div>
			</div>
						
			<div id="buttons">
				<c:if test="${currMessage.user.id == user_id}" > 								
					<form action="/liaison/public/message/delete/${currMessage.id}" method="post">						
						<input type="hidden" name="_method" value="delete"/>
						<button class="btn btn-danger rounded mr-2" type="submit"> Delete </button>
						</form>
				</c:if>
			</div>
		</div>
		
		<!-- REPLY FORM -->
		<div class="mt-4 mb-4" align="right">
			<form:form action="/liaison/reply/post" method="post" modelAttribute="reply">
				<form:hidden path="publicMessage" value="${currMessage.id}"/>
				<form:hidden path="user" value="${user_id}" />
				<form:textarea path="body" cols="5" rows="5" placeholder="Type your reply here" class="form-control input-lg"/>
				<form:errors path="body" class="text-danger"/>
				<button type="submit" class="btn btn-success rounded"> Post Reply </button>
			</form:form>
		</div>
		
		<!-- REPLIES -->
		<div>
			<c:forEach var="reply" items="${currMessage.replies}">	
						
				<div id="messageContainer2" class="col">
					
					<div id="parent">
						<div id="child1">
							<c:if test="${reply.user.avi != null}" >
								<img src="/liaison/user/avi/view/<c:out value='${reply.user.id}'/>" id="avi2"/>
							</c:if>
						</div>
						
						<div id="child2">
							<div id="baby">
								<p> <a href="/liaison/user/${reply.user.id}"> <c:out value="${reply.user.userName}"/> </a> </p>
								<p id="date"> <fmt:formatDate value="${reply.createdAt}" /> </p>
							</div>
							<div>
								<p class="text-wrap"> <c:out value="${reply.body}"/> </p>
							</div>
						</div>
					</div>
						
					<div id="buttons">
						<c:if test="${reply.user.id == user_id}" > 								
								<form action="/liaison/reply/delete/${reply.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
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
				<div id="messageContainer2">
					
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