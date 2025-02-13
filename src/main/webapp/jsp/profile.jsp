<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<html>
<head>
<title>Your Profile</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
	<div class="container">
		<h2>Your Profile</h2>
		<c:if test="${not empty message}">
			<p class="success">${message}</p>
		</c:if>
		<c:if test="${not empty errorMessage}">
			<p class="error">${errorMessage}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/profile"
			method="post">
			<label for="username">Username:</label> <input type="text"
				name="username" id="username" value="${user.username}" required />
			<label for="password">Password:</label> <input type="text"
				name="password" id="password" value="${user.password}" required />
			<input type="submit" value="Update Profile" />
		</form>

		<c:if test="${user.role ne 'COMPANY'}">
			<h3>Your Subscriptions</h3>
			<c:choose>
				<c:when test="${not empty subscriptions}">
					<ul>
						<c:forEach var="offer" items="${subscriptions}">
							<li><strong>${offer.departureCity} to
									${offer.arrivalCity}</strong><br /> Departure Time:
								${offer.departureTime}</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<p>You have not subscribed to any offers yet.</p>
				</c:otherwise>
			</c:choose>
			<a href="${pageContext.request.contextPath}/offers">Back to
				Offers</a>
		</c:if>
		
		<c:if test="${user.role ne 'USER'}">
		<a href="${pageContext.request.contextPath}/companyDashboard">Back to dashboard</a>
		</c:if>
		<p>
			
		</p>
	</div>
</body>
</html>