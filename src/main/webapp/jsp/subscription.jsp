<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Subscription Confirmation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/subscription.css" />
</head>
<body>
    <div class="container">
        <h2>Subscription Confirmation</h2>
        <c:if test="${not empty message}">
            <p class="success">${message}</p>
        </c:if>
        <c:if test="${not empty offer}">
            <p>You have subscribed to the following offer:</p>
            <p>
                <strong>${offer.departureCity} to ${offer.arrivalCity}</strong><br/>
                Departure Time: ${offer.departureTime}<br/>
                Subscription Period: ${offer.startDate} to ${offer.endDate}
            </p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error">${errorMessage}</p>
        </c:if>
        <p>
            <a href="${pageContext.request.contextPath}/offers">Back to Offers</a>
        </p>
    </div>
</body>
</html>