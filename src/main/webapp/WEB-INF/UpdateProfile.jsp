<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/UpdateProfilePage.css" />
 <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title> Update Profile / Liaison</title>
</head>
<body class="container">
<div class="col">

	<div class="row mt-5 mb-4">
		<div class="col" align="left">
			<h1> Update Account </h1>
		</div>
		<div class="col" align="right">
			<p> <a href="/liaison/user/${user_id}"> Back to Profile Page </a> </p>
		</div>
	</div>
	
	<div>
		<form:form action="/liaison/user/update/put" method="post" modelAttribute="user">
			<input type="hidden" name="_method" value="put" />
			<form:hidden path="id" value="${currUser.id}" />
			<div>
				<label> Username: </label>
				<form:input path="userName" value="${currUser.userName}" class="form-control"/>
				<form:errors path="userName" class="text-danger"/>
			</div>
			<div>
				<label> Email: </label>
				<form:input path="email" value="${currUser.email}" class="form-control"/>
				<form:errors path="email" class="text-danger"/>
			</div>
			<div>
				<label> Bio: </label>
				<form:input path="bio" class="form-control" value="${currUser.bio}"/>
				<form:errors path="bio" class="text-danger"/>
			</div>
			<div>
				<label> Password: </label>
				<form:password path="password" class="form-control"/>
				<form:errors path="password" class="text-danger"/>
			</div>
			<div>
				<label> Confirm Password: </label>
				<form:password path="confirmPassword" class="form-control"/>
				<form:errors path="confirmPassword" class="text-danger"/>
			</div>
			<button type="submit" class="btn btn-primary rounded mt-2"> Update Account </button>
		</form:form>
	</div>
		
	<div class="row">
			
		<div class="col mt-3 ml-3">
			<form action="/liaison/user/avi/post" method="post" enctype="multipart/form-data" class="form-group">
				<label> Avi :</label>
				<input type="file" name="image" class="form-control-file"/>
				<button type="submit" class="btn btn-primary rounded mt-2"> Update Avi </button>
				<c:if test="${fileError1 != null}">
					<p class="text-danger"> <c:out value="${fileError1}"/> </p>
				</c:if>	
			</form>
		</div>
		
		<div class="col mt-3 ml-3">
			<form action="/liaison/user/header/post" method="post" enctype="multipart/form-data" class="form-group">
				<label> Header :</label>
				<input type="file" name="image" class="form-control-file"/>
				<button type="submit" class="btn btn-primary rounded mt-2"> Update Header </button>
				<c:if test="${fileError2 != null}">
					<p class="text-danger"> <c:out value="${fileError2}"/> </p>
				</c:if>			
			</form>
		</div>
				
		<div class="col" id="mt-3">
			<form action="/liaison/user/delete" method="post" class="form-group" >
				<input type="hidden" name="_method" value="delete"/>
				<button class="btn-danger rounded mt-4" type="submit" id="deleteBtn"> Delete Account </button>
			</form>
		</div>
				
	</div>
	
	<div>
		<c:if test="${fileError3 != null}">
			<p class="text-danger"> <c:out value="${fileError3}"/> </p>
		</c:if>
	</div>
	
</div>
</body>
</html>