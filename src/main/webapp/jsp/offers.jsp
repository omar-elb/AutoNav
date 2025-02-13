<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Available Shuttle Offers</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <div class="container">
        <h2>Available Shuttle Offers</h2>
        <c:choose>
            <c:when test="${not empty offers}">
                <ul>
                    <c:forEach var="offer" items="${offers}">
                     <c:if test="${ offer.currentSubscribers + 1 < offer.targetSubscribers }">
                        <li>
                            <strong>
                                <a href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}">
                                    ${offer.departureCity} to ${offer.arrivalCity}
                                </a>
                            </strong>
                            <br/>Departure Time: ${offer.departureTime} | 
                            Subscription Period: ${offer.startDate} to ${offer.endDate}
                            <br/>
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <c:choose>
                                        <c:when test="${subscribedOfferIds != null and subscribedOfferIds.contains(offer.id)}">

                                            <button type="button" disabled>Applied</button>
                                        </c:when>
                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}">View Details and Subscribe</a>
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
