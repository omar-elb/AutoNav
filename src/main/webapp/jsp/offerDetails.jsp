<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Offer Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/offerDetails.css" />
</head>
<body>
    <div class="container">
        <h2>Offer Details</h2>
        <c:if test="${not empty offer}">
            <p>
                <strong>Route:</strong> ${offer.departureCity} to ${offer.arrivalCity}<br/>
                <strong>Departure Time:</strong> ${offer.departureTime}<br/>
                <strong>ArrivalTime Time:</strong> ${offer.arrivalTime}<br/>
                <strong>Subscription Period:</strong> ${offer.startDate} to ${offer.endDate}<br/>
                <strong>Description:</strong> ${offer.description}<br/>
                <strong>Subscribers:</strong> ${offer.currentSubscribers} / ${offer.targetSubscribers}
            </p>
			<c:choose>
				<c:when test="${not empty sessionScope.user}">
					<c:choose>
						<c:when test="${offer.currentSubscribers == offer.targetSubscribers and offersInterestedInIds != null and offersInterestedInIds.contains(offer.id)}">
							<p>We have reached the target number of subscribers.<br> You can express your interest in this offer here. </p>
							<button type="button" disabled>Interested</button>
						</c:when>
						<c:when test="${offer.currentSubscribers == offer.targetSubscribers}">
						    <p>We have reached the target number of subscribers.<br> You can express your interest in this offer here. </p>
							<form action="${pageContext.request.contextPath}/offerInterest" method="post">
								<input type="hidden" name="offerId" value="${offer.id}" />
								<button type="submit">interest</button>
							</form>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${subscribedOfferIds != null and subscribedOfferIds.contains(offer.id)}">
									<button type="button" disabled>Applied</button>
								</c:when>
								<c:otherwise>
									<form action="${pageContext.request.contextPath}/subscribe"
										method="post">
										<input type="hidden" name="offerId" value="${offer.id}" />
										<button type="submit">Subscribe</button>
									</form>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<a href="${pageContext.request.contextPath}/jsp/login.jsp">Login
						to Subscribe</a>
				</c:otherwise>
			</c:choose>
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