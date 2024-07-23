package com.chainsys.parksmart.validation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

@Repository
public class Validation {

	public boolean validateUserId(int userId) {
		return userId > 0;
	}

	public boolean validateUserName(String userName) {
		return userName != null && userName.length() >= 3 && userName.length() <= 100;
	}

	public boolean validateUserPassword(String userPassword) {
		return userPassword != null && userPassword.length() >= 6 && userPassword.length() <= 20;
	}

	public boolean validatePhoneNumber(String phoneNumber) {
		return phoneNumber != null && phoneNumber.matches("\\d{10}");
	}

	public boolean validateEmail(String email) {
		return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{2,}");
	}

	public boolean validateSpotId(int spotId) {
		return spotId > 0;
	}

	public boolean validateLocationName(String locationName) {
		return locationName != null && locationName.length() >= 3 && locationName.length() <= 100;
	}

	public boolean validateAddress(String address) {
		return address != null && address.length() >= 5 && address.length() <= 255;
	}

	public boolean validateVehicleType(String vehicleType) {
		return vehicleType != null && vehicleType.length() >= 1 && vehicleType.length() <= 30;
	}

	public boolean validateSpotNumber(String spotNumber) {
		return spotNumber != null && spotNumber.matches("^[a-zA-Z0-9]*$") && spotNumber.length() <= 20;
	}

	public boolean validateCountSpotNumber(int countSpotNumber) {
		return countSpotNumber > 0;
	}

	public boolean validateReservationId(int reservationId) {
		return reservationId > 0;
	}

	public boolean validateNumberPlate(String numberPlate) {
		return numberPlate != null && numberPlate.length() <= 100;
	}

	public boolean validatePrice(int price) {
		return price > 0;
	}

	public boolean validatePaymentMethod(String paymentMethod) {
		return paymentMethod != null && (paymentMethod.equals("Credit Card") || paymentMethod.equals("Debit Card"));
	}

	public boolean validateCardNumber(String cardNumber) {
		return cardNumber != null && cardNumber.matches("^\\d{16}$");
	}

	public boolean validateExpiryDate(String expiryDate) {
		return expiryDate != null && expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$");
	}

	public boolean validateCvv(String cvv) {
		return cvv != null && cvv.matches("^\\d{3}$");
	}

	public boolean validateDateTime(String startDateTime, String endDateTime) {
		LocalDateTime start = LocalDateTime.parse(startDateTime);
		LocalDateTime end = LocalDateTime.parse(endDateTime);
		LocalDateTime currentDateTime = LocalDateTime.now();
		return start.isBefore(end) && start.isAfter(currentDateTime) && end.isAfter(currentDateTime);
	}

}
