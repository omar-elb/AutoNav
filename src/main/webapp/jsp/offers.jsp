<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="header.jsp"%>

<html>
<head>
<title>Available Offers</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/offers.css" />
</head>
<body>
	<div class="container">
		<h2>Available Offers</h2>

		<form method="get" action="${pageContext.request.contextPath}/offers"
			class="search-form">
			<input type="text" name="departureCity" placeholder="Departure City"
				value="${param.departureCity}" /> <input type="text"
				name="arrivalCity" placeholder="Arrival City"
				value="${param.arrivalCity}" /><br> <input type="text"
				name="departureTime" placeholder="Departure Time ex:'17:00'"
				value="${param.departureTime}" /> <input type="text"
				name="arrivalTime" placeholder="Arrival Time ex:'19:00'"
				value="${param.arrivalTime}" /><br> <input type="number"
				name="price" placeholder="Max Price" value="${param.price}" />
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
							<a href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}"> ${offer.departureCity} to ${offer.arrivalCity} </a>
							</strong> <br /> 
							<b>Departure Time:</b> ${offer.departureTime} |
							<b>Arrival Time:</b> ${offer.arrivalTime} | 
							<b>Price:</b> $${offer.price} <br /> 
							Subscription Period: ${offer.startDate} to ${offer.endDate} <br /> 
								<c:choose>
									<c:when test="${not empty sessionScope.user}">
										<c:choose>
											<c:when
												test="${offer.currentSubscribers == offer.targetSubscribers and offersInterestedInIds != null and offersInterestedInIds.contains(offer.id)}">
												<p>We have reached the target number of subscribers.<br> You can express your interest in this offer here. </p>
												<button type="button" disabled>Interested</button>
											</c:when>
											<c:when
												test="${offer.currentSubscribers == offer.targetSubscribers}">
												<p>We have reached the target number of subscribers.<br> You can express your interest in this offer here. </p>
												<form
													action="${pageContext.request.contextPath}/offerInterest"
													method="post">
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
														<a
															href="${pageContext.request.contextPath}/offerDetails?offerId=${offer.id}">
															View Details and Subscribe </a>
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


