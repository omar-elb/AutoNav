<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Add Offer</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addOffer.css" />
</head>
<body>
    <div class="container">
        <h2>Add Offer</h2>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
            <form action="${pageContext.request.contextPath}/addOffer" method="post">
                <label for="departureCity">Departure City:</label>
                <input type="text" name="departureCity" id="departureCity" required />
                
                <label for="arrivalCity">Arrival City:</label>
                <input type="text" name="arrivalCity" id="arrivalCity" required />
                
                <label for="departureTime">Departure Time:</label>
                <input type="text" name="departureTime" id="departureTime" required />
                
                <label for="arrivalTime">Arrival Time:</label>
                <input type="text" name="arrivalTime" id="arrivalTime" required />
                
                <label for="startDate">Start Date:</label>
                <input type="date" name="startDate" id="startDate" required />
                
                <label for="endDate">End Date:</label>
                <input type="date" name="endDate" id="endDate" required />
                
                <label for="targetSubscribers">Target Subscribers:</label>
                <input type="text" name="targetSubscribers" id="targetSubscribers" required />
                
                <label for="price">Price:</label>
                <input type="text" name="price" id="price" required />
                
                <label for="description">Description:</label>
                <textarea name="description" id="description" required></textarea>
                
                <input type="submit" value="Add Offer" />
            </form>
        <p>
            <a href="${pageContext.request.contextPath}/companyDashboard">Back to Dashboard</a>
        </p>
    </div>
</body>
</html>