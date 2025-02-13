<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required />
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required />
            <input type="submit" value="Login" />
        </form>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
        <p>
            Don't have an account? 
            <a href="${pageContext.request.contextPath}/jsp/register.jsp">Register here</a>.
        </p>
    </div>
</body>
</html>