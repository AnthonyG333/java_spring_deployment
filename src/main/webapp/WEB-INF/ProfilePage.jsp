<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/ProfilePage.css" />	
	<link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> <c:out value="${currUser.userName}"/>'s Profile </title>
</head>
<body>
<div align="left" class="m-4">
	<h1 style="color:black"> <c:out value="${currUser.userName}"/>'s Liaison </h1>
</div>
<div  class="row" id="container">

	<div class="col" id="column1" >
		<h2 class="row" id="navItem"> <a href="/liaison/home/1" style="color:blue"> Home </a> </h2> 
		<h2 class="row" id="navItem"> <a href="/liaison/user/${user_id}" style="color:blue"> Profile </a> </h2>
		<h2 class="row" id="navItem"> <a href="/liaison/private/messages" style="color:blue"> Private Messages </a> </h2>
		<h4 class="row" id="navItem"> <a href="/liaison/logout" style="color:blue"> Logout </a> </h4>
	</div>
	
	<div class="col p-0" id="column2">
		
		<div style="background-color: lightblue">	
			<div class="row m-0">
			<!-- HEADER -->
				<c:choose>
			
					<c:when test="${currUser.header != null }">
						<img src="/liaison/user/header/view/<c:out value='${currUser.id}'/>" id="header"/>
					</c:when>
				
					<c:otherwise>
						<c:choose>
					
							<c:when test="${currUser.id == user_id}">
								<div id="headerError">
									<p align="left" class="text-danger text-wrap font-weight-bold"> You have not uploaded a header. </p>
								</div>
							</c:when>
						
							<c:otherwise>
								<div id="headerError">
									<p align="left" class="text-danger text-wrap font-weight-bold"> <c:out value="${currUser.userName}"/> has not uploaded a header. </p>
								</div>
							</c:otherwise>
						
						</c:choose>
					</c:otherwise>
			
				</c:choose>
			</div>
		
			<div id="parentBox" class="p-4"> 
			<!-- AVI, USERNAME, BIO, UPDATE/ADD -->	
			
				<div id="childBox" align="center">
				<!-- AVI -->	
					<c:choose>
				
						<c:when test="${currUser.avi != null }">
							<img src="/liaison/user/avi/view/<c:out value='${currUser.id}'/>"  id="avi1"/>
						</c:when>
					
						<c:otherwise>
							<c:choose>
						
								<c:when test="${currUser.id == user_id}">
									<div class="m-2">
										<p class="text-danger text-wrap font-weight-bold"> You have not uploaded an avi. </p>
									</div>
								</c:when>
							
								<c:otherwise>
									<div class="m-2">
										<p class="text-danger text-wrap font-weight-bold"> <c:out value="${currUser.userName}"/> has not uploaded an avi. </p>
									</div>
								</c:otherwise>
							
							</c:choose>
						</c:otherwise>
					
					</c:choose>
				</div>
				
				<!-- USERNAME & BIO -->
				<div id="childBox" align="center">
					<h2 style="color:red"> <c:out value="${currUser.userName}"/> </h2>
					<p class="font-weight-bold text-wrap" id="bio"> <c:out value="${currUser.bio}" /> </p>
				</div>
			
				<div id="childBox" align="center">
				<!-- UPDATE/ADD -->
					<c:choose>
									
						<c:when test="${currUser.id == user_id}">
							<p  class="btn btn-info rounded"> <a style="color: white" href="/liaison/user/update/get" > Update Profile </a> </p>
						</c:when>
					
						<c:otherwise>
							<form:form action="/liaison/friend/post" method="post" modelAttribute="friend" >
								<form:hidden path="user" value="${user_id}"  />
								<form:hidden path="userName" value="${currUser.userName}"  />
								<form:hidden path="friendId" value="${currUser.id}" />
								<button type="submit" class="btn btn-warning rounded"> Add friend </button>
							</form:form>
						</c:otherwise>	
						
					</c:choose>
					<c:if test="${friendError != null}">
						<p class="text-danger"> <c:out value="${friendError}"/> </p>
					</c:if>
				</div>
			</div>			
		</div>
			
		<div id="messageCol">
		<!-- ALL PUBLIC MESSAGES -->
			<h4 align="center"> <a href="/liaison/user/${currUser.id}"> Public Messages </a> | <a href="/liaison/user/replies/${currUser.id}"> Replies </a> </h4>	
			<c:choose>
			
			
				<c:when test="${currUser.publicMessages.size() == 0}">
					<c:choose>
						<c:when test="${currUser.id == user_id}">
							<p class="text-danger font-weight-bold m-2"> You have not posted any public messages. </p>
						</c:when>
						<c:otherwise>
							<p class="text-danger font-weight-bold m-2"> <c:out value="${currUser.userName}"/> has not posted any public messages. </p>
						</c:otherwise>
					</c:choose>
				</c:when>
			
				<c:otherwise>	
					<c:forEach var="message" items="${currUser.publicMessages}" >
				
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
								<form action="/liaison/public/message/delete/${message.id}" method="post">						
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn-sm btn-danger rounded mr-2" type="submit"> Delete </button>
								</form>
							</c:if>
						<a href="/liaison/reply/get/${message.id}" class="btn-sm btn-success rounded">️  Replies: <c:out value="${message.popularity}"/> </a>
				 	</div>
				 	
				</div>
					</c:forEach>
				</c:otherwise>
			
			</c:choose>
			
		</div>
		
	</div>
	
	
	<div class="col" align="center" id="column3">	
		<h1 align="center"> <c:out value="${currUser.userName}"/>'s Friends </h1>
		<table>
		<c:forEach var="friend" items="${currUser.friends}">
			<tr id="friendContainer">
				<td class="p-2">
					<img src="/liaison/user/avi/view/<c:out value='${friend.friendId}'/>" id="avi2"/>
				</td>
				<td class="p-2"> 
					<p class="text-dark" id="userName"> <a href="/liaison/user/${friend.friendId}"> <c:out value="${friend.userName}"/> </a> </p>
				</td>
				<c:if test="${currUser.id == user_id}">
				<td class="p-2">
					<form action="/liaison/friend/delete/${friend.id}" method="post">
						<input type="hidden" name="_method" value="delete" />
						<button class="btn-sm btn-warning rounded" type="submit"> Remove Friend </button>
					</form> 
				</td>
				</c:if>
			</tr>
		</c:forEach>
		</table>
	</div>
	
</div>
</body>
</html>