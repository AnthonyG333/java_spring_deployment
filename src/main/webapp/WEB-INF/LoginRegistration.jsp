<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/LoginRegistration.css" />
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.2/css/bootstrap.min.css" />
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Liaison - Login & Registration </title>
</head>
<body>
	<div class="container">
	<h1 align="center" id="header"> Welcome to Liaison! A messaging application. </h1>
		<div class="row">
		
			<div class="col" id="form">
				<h1 style="color:blue"> New User? Create an account: </h1>
				<form:form action="/liaison/user/register" method="post" modelAttribute="user">
					<div class="form-group p-2">
						<label> Username: </label>
						<form:input path="userName" class="form-control"/>
						<form:errors path="userName" class="text-danger"/>
					</div>
					<div class="form-group p-2">
						<label> Email: </label>
						<form:input path="email" class="form-control"/>
						<form:errors path="email" class="text-danger"/>
					</div>
					<div class="form-group p-2">
						<label> Password: </label>
						<form:password path="password" class="form-control"/>
						<form:errors path="password" class="text-danger"/>
					</div>
					<div class="form-group p-2">
						<label> Confirm Password: </label>
						<form:password path="confirmPassword" class="form-control"/>
						<form:errors path="confirmPassword" class="text-danger"/>
					</div>
					<button class="btn btn-primary p-2"> Create Account </button>
				</form:form>
			</div>
			
			<div class="col" id="form">
				<h1 style="color:blue"> Returning User? Login below: </h1>
				<form:form action="/liaison/user/login" method="post" modelAttribute="loginUser">
					<div class="form-group p-2">
						 <label> Email: </label>
						 <form:input path="email" class="form-control"/>
						 <form:errors path="email" class="text-danger"/>
					</div>
					<div class="form-group p-2">
						<label> Password: </label>
						<form:password path="password" class="form-control"/>
						<form:errors path="password" class="text-danger"/>
					</div>
					<button class="btn btn-primary p-2"> Login </button>
				</form:form>
			</div>
			
		</div>
	</div>
</body>
</html>