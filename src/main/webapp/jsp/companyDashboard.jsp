<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Company Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <div class="container">
        <h2>Company Dashboard</h2>
        <p>Welcome, <strong>${sessionScope.user.username}</strong>.</p>
        
        <c:if test="${not empty sessionScope.message}">
            <p class="success">${sessionScope.message}</p>
            <c:remove var="message" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <p class="error">${sessionScope.errorMessage}</p>
            <c:remove var="errorMessage" scope="session" />
        </c:if>
        
        <div style="text-align: right; margin-bottom: 15px;">
            <a href="${pageContext.request.contextPath}/addOffer" class="button">Add New Offer</a>
        </div>
        
        <c:if test="${not empty companyOffers}">
            <table border="1" width="100%" cellpadding="5" cellspacing="0">
                <thead>
                    <tr>
                        <th>Route</th>
                        <th>Departure</th>
                        <th>Arrival</th>
                        <th>Period</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="offer" items="${companyOffers}">
                        <tr>
                            <td>${offer.departureCity} to ${offer.arrivalCity}</td>
                            <td>${offer.departureTime}</td>
                            <td>${offer.arrivalTime}</td>
                            <td>${offer.startDate} - ${offer.endDate}</td>
                            <td>${offer.description}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/updateOffer?offerId=${offer.id}">Update</a>
                                |
                                <a href="${pageContext.request.contextPath}/deleteOffer?offerId=${offer.id}" 
                                   onclick="return confirm('Are you sure you want to delete this offer?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty companyOffers}">
            <p>You have not provided any offers yet.</p>
        </c:if>
    </div>
</body>
</html>