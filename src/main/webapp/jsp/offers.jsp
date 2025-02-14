<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Available Offers</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <div class="container">
        <h2>Available Offers</h2>

        <form method="get" action="${pageContext.request.contextPath}/offers" class="search-form">
            <input type="text" name="departureCity" placeholder="Departure City" value="${param.departureCity}" />
            <input type="text" name="arrivalCity" placeholder="Arrival City" value="${param.arrivalCity}" />
            <input type="time" name="departureTime" placeholder="Departure Time" value="${param.departureTime}" />
            <input type="time" name="arrivalTime" placeholder="Arrival Time" value="${param.arrivalTime}" />
            <input type="number" name="price" placeholder="Max Price" value="${param.price}" />
            <button type="submit">Search</button>
        </form>

        <hr />

        <c:choose>
            <c:when test="${not empty offers}">
                <ul>
                    <c:forEach var="offer" items="${offers}">
                        <c:if test="${offer.currentSubscribers + 1 < offer.targetSubscribers}">
                            <li>
                                <strong>
                                    <a href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}">
                                        ${offer.departureCity} to ${offer.arrivalCity}
                                    </a>
                                </strong>
                                <br />
                                <b>Departure Time:</b> ${offer.departureTime} | 
                                <b>Arrival Time:</b> ${offer.arrivalTime} | 
                                <b>Price:</b> $${offer.price} 
                                <br />
                                Subscription Period: ${offer.startDate} to ${offer.endDate}
                                <br />
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user}">
                                        <c:choose>
                                            <c:when test="${subscribedOfferIds != null and subscribedOfferIds.contains(offer.id)}">
                                                <button type="button" disabled>Applied</button>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}">
                                                    View Details and Subscribe
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/jsp/login.jsp">Login to Subscribe</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p>No offers available at the moment.</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>


