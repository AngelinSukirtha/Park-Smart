package com.chainsys.parksmart.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Transaction {
	private int userId;
	private int transactionId;
	private int reservationId;
	private int price;
	private String paymentMethod;
	private String transactionTime;
	private String cardNumber;
	private String expiryDate;
	private String cvv;
	private String paymentStatus;

	public Transaction(int userId, int transactionId, int reservationId, int price, String paymentMethod,
			String transactionTime, String cardNumber, String expiryDate, String cvv, String paymentStatus) {
		super();
		this.userId = userId;
		this.transactionId = transactionId;
		this.reservationId = reservationId;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.transactionTime = transactionTime;
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
		this.paymentStatus = paymentStatus;
	}

	@Autowired
	public Transaction() {
	}

	@Override
	public String toString() {
		return "Transaction [userId=" + userId + ", transactionId=" + transactionId + ", reservationId=" + reservationId
				+ ", price=" + price + ", paymentMethod=" + paymentMethod + ", transactionTime=" + transactionTime
				+ ", cardNumber=" + cardNumber + ", expiryDate=" + expiryDate + ", cvv=" + cvv + ", paymentStatus="
				+ paymentStatus + ", getUserId()=" + getUserId() + ", getTransactionId()=" + getTransactionId()
				+ ", getReservationId()=" + getReservationId() + ", getPrice()=" + getPrice() + ", getPaymentMethod()="
				+ getPaymentMethod() + ", getTransactionTime()=" + getTransactionTime() + ", getCardNumber()="
				+ getCardNumber() + ", getExpiryDate()=" + getExpiryDate() + ", getCvv()=" + getCvv()
				+ ", getPaymentStatus()=" + getPaymentStatus() + "]";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
