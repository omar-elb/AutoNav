<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
<title>Register</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
	<div class="container">
		<h2>Register</h2>
		<form action="${pageContext.request.contextPath}/register" method="post">
			<label for="username">Username:</label> 
			<input type="text" name="username" id="username" required /> 
			<label for="password">Password:</label>
			<input type="password" name="password" id="password" required /> 
			<label for="role">Choose a role:</label> 
			<select name="role" id="role">
				<option value="USER">Simple user</option>
				<option value="COMPANY">Company</option>
			</select> 
			<input type="submit" value="Register" />
		</form>
		<c:if test="${not empty errorMessage}">
			<p class="error">${errorMessage}</p>
		</c:if>
		<p>
			Already have an account? <a
				href="${pageContext.request.contextPath}/jsp/login.jsp">Login
				here</a>.
		</p>
	</div>
</body>
</html>