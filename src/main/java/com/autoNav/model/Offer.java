package com.autoNav.model;

import java.util.Date;

public class Offer {
	private int id;
	private int companyId;
	private Date startDate;
	private Date endDate;
	private String departureCity;
	private String arrivalCity;
	private String departureTime;
	private String arrivalTime;
	private int targetSubscribers;
	private int currentSubscribers;
	private double price;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getTargetSubscribers() {
		return targetSubscribers;
	}

	public void setTargetSubscribers(int targetSubscribers) {
		this.targetSubscribers = targetSubscribers;
	}

	public int getCurrentSubscribers() {
		return currentSubscribers;
	}

	public void setCurrentSubscribers(int currentSubscribers) {
		this.currentSubscribers = currentSubscribers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
