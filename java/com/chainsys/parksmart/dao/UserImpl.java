package com.chainsys.parksmart.dao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.chainsys.parksmart.mapper.*;
import com.chainsys.parksmart.model.Addresses;
import com.chainsys.parksmart.model.Locations;
import com.chainsys.parksmart.model.Reservation;
import com.chainsys.parksmart.model.Spots;
import com.chainsys.parksmart.model.Transaction;
import com.chainsys.parksmart.model.User;

@Repository
public class UserImpl implements UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean userRegistration(User user) {
		String selectQuery = "SELECT user_id FROM user WHERE user_name=? AND user_password=? AND phone_number=? AND email=?";

		List<Integer> existingUserIds = jdbcTemplate.queryForList(selectQuery, Integer.class, user.getUserName(),
				user.getUserPassword(), user.getPhoneNumber(), user.getEmail());

		if (!existingUserIds.isEmpty()) {
			return false;
		}

		String insertQuery = "INSERT INTO user (user_name, user_password, phone_number, email) VALUES (?, ?, ?, ?)";
		Object[] params = { user.getUserName(), user.getUserPassword(), user.getPhoneNumber(), user.getEmail() };
		int rowsAffected = jdbcTemplate.update(insertQuery, params);
		return rowsAffected > 0;
	}

	@Override
	public boolean userLogin(User user) {
		String selectQuery = "SELECT COUNT(*) FROM user WHERE email=?";
		int count = jdbcTemplate.queryForObject(selectQuery, Integer.class, user.getEmail());
		return count == 1;
	}

	public Integer getUserById(User user) {
		String selectQuery = "SELECT user_id FROM user WHERE email=?";
		Object[] details = { user.getEmail() };
		return jdbcTemplate.queryForObject(selectQuery, Integer.class, details);
	}

	public List<Integer> getSpotsById(int id) {
		String selectQuery = "SELECT spot_id FROM spots WHERE user_id=?";
		return jdbcTemplate.queryForList(selectQuery, Integer.class, id);
	}

	public void insertSpots(Spots spots, int id, String locationName, String address, String vehicleType,
			String[] strArr) {
		String insertQuery = "INSERT INTO spots (user_id, location, address_name, vehicle_type, spot_number) VALUES (?, ?, ?, ?, ?)";
		for (String spotNumber : strArr) {
			Object[] params = { id, locationName, address, vehicleType, spotNumber };
			jdbcTemplate.update(insertQuery, params);
		}
	}

	public void updateSpotStatus(List<Integer> spotIdList) {
		String updateQuery = "UPDATE spots SET spot_status = 'occupied' WHERE spot_id = ?";
		for (Integer spotId : spotIdList) {
			jdbcTemplate.update(updateQuery, spotId);
		}
	}

	public List<String> readSpotNumbers(String locationName) {
		String selectQuery = "SELECT spot_number FROM spots WHERE location = ? AND spot_status = 'occupied'";
		return jdbcTemplate.queryForList(selectQuery, String.class, locationName);
	}

	public String getLocationByLocationId(int locationId) {
		String selectQuery = "SELECT location FROM locations WHERE location_id = ?";
		Object[] details = { locationId };
		return jdbcTemplate.queryForObject(selectQuery, String.class, details);
	}

	public void updateSpotStatus(Spots spots) {
		String updateQuery = "UPDATE spots SET spot_status=? WHERE spot_id = ?";
		Object[] params = { spots.getSpotStatus(), spots.getSpotId() };
		jdbcTemplate.update(updateQuery, params);
	}

	public int countSpotNumber(Spots spots, int id) {
		String selectQuery = "SELECT COUNT(spot_number) FROM spots WHERE user_id = ? AND spot_status = 'occupied'";
		Integer spotCount = jdbcTemplate.queryForObject(selectQuery, Integer.class, id);
		return spotCount != null ? spotCount : 0;
	}

	public int getReservationByReservationId(int id) {
		String selectQuery = "SELECT reservation_id FROM reservation WHERE user_id = ? and reservation_status='pending'";
		Object[] details = { id };
		return jdbcTemplate.queryForObject(selectQuery, Integer.class, details);
	}

	public void insertReservation(Reservation reservation, int id) {
		String insertQuery = "INSERT INTO reservation (user_id, reservation_id, number_plate, start_date_time, end_date_time, is_active) VALUES (?, ?, ?, ?, ?, ?)";
		Object[] params = { id, reservation.getReservationId(), reservation.getNumberPlate(),
				reservation.getStartDateTime(), reservation.getEndDateTime(), reservation.getIsActive() };
		jdbcTemplate.update(insertQuery, params);
	}

	public List<Reservation> readReservation() {
		String selectQuery = "SELECT user_id, reservation_id, number_plate, start_date_time, end_date_time, reservation_status, is_active FROM reservation";
		return jdbcTemplate.query(selectQuery, new ReservationMapper());
	}

	public void updateReservationStatus(Reservation reservation) {
		String updateQuery = "UPDATE reservation SET reservation_status=? WHERE reservation_id = ?";
		Object[] params = { reservation.getReservationStatus(), reservation.getReservationId() };
		jdbcTemplate.update(updateQuery, params);
	}

	public void updateReservation(int reservationId) {
		String updateQuery = "UPDATE reservation SET reservation_status='approved' WHERE reservation_id = ?";
		jdbcTemplate.update(updateQuery, reservationId);
	}

	public void updateIsActive(Reservation reservation) {
		String updateQuery = "UPDATE reservation SET is_active=? WHERE reservation_id = ?";
		Object[] params = { reservation.getIsActive(), reservation.getReservationId() };
		jdbcTemplate.update(updateQuery, params);
	}

	public List<Reservation> readReservation(Reservation reservation) {
		String selectQuery = "SELECT reservation_id FROM reservation WHERE user_id=? and is_active=true";
		Object[] params = { reservation.getUserId() };
		return jdbcTemplate.query(selectQuery, new ReservationMapper(), params);
	}

	public void insertTransaction(int reservationId, int id, int price, String transactionTime) {
		String insertQuery = "INSERT INTO transaction (user_id, reservation_id, price, payment_method, transaction_time, payment_status) "
				+ "VALUES (?, ?, ?, '', ?, ' ')";
		Object[] params = { id, reservationId, price, transactionTime };
		jdbcTemplate.update(insertQuery, params);
	}

	public int getPrice(Transaction transaction, String vehicleType, String startDateTime, String endDateTime) {
		LocalDateTime start = parseDateTime(startDateTime);
		LocalDateTime end = parseDateTime(endDateTime);
		int price = 0;

		if (start != null && end != null) {
			price = calculatePrice(vehicleType, start, end);
			transaction.setPrice(price);
		}
		return price;
	}

	public String getCurrentTransactionTimeFormatted(Transaction transaction) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String transactionTime = LocalDateTime.now().format(formatter);
		transaction.setTransactionTime(transactionTime);
		return transactionTime;
	}

	public void updateTransaction(Transaction transaction, int reservationId) {
		String updateQuery = "UPDATE transaction SET card_number=?, expiry_date=?, cvv=? WHERE reservation_id = ?";
		Object[] params = { transaction.getCardNumber(), transaction.getExpiryDate(), transaction.getCvv(),
				reservationId };
		jdbcTemplate.update(updateQuery, params);
	}

	public void addPaymentMethod(Transaction transaction, int id, String paymentMethod) {
		String updateQuery = "UPDATE transaction SET payment_method = ?, payment_status='paid' WHERE user_id = ?";
		Object[] params = { paymentMethod, id };
		jdbcTemplate.update(updateQuery, params);
	}

	public LocalDateTime parseDateTime(String dateTimeString) {
		if (dateTimeString != null) {
			return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		} else {
			return null;
		}
	}

	public int calculatePrice(String vehicleType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		Duration duration = Duration.between(startDateTime, endDateTime);
		long hours = duration.toHours();
		int hourlyRate;
		switch (vehicleType) {
		case "Car":
			hourlyRate = 20;
			break;
		case "Bike":
			hourlyRate = 10;
			break;
		case "Truck":
			hourlyRate = 30;
			break;
		default:
			hourlyRate = 20;
			break;
		}
		return (int) (hours * hourlyRate);
	}

	public List<Reservation> readReservations() {
		String selectQuery = "SELECT user_id, reservation_id, number_plate, start_date_time, end_date_time, fine_amount, reservation_status, is_active FROM reservation";
		return jdbcTemplate.query(selectQuery, new ReservationMapper());
	}

	public List<Transaction> readTransaction() {
		String selectQuery = "SELECT user_id, reservation_id, transaction_id, price, payment_method, transaction_time, card_number, expiry_date, cvv, payment_status FROM transaction";
		return jdbcTemplate.query(selectQuery, new TransactionMapper());
	}

	public Transaction readTransactions(Transaction transaction) {
		String selectQuery = "SELECT price, transaction_time FROM transaction WHERE user_id=?";
		Object[] params = { transaction.getUserId() };
		jdbcTemplate.query(selectQuery, new TransactionMapper(), params);
		return transaction;
	}

	public List<User> readUser() {
		String selectQuery = "SELECT user_id, user_name, user_password, phone_number, email FROM user where status=1";
		return jdbcTemplate.query(selectQuery, new UserMapper());
	}

	public List<Spots> readSpotsAdmin() {
		String selectQuery = "SELECT user_id, spot_id, location, address_name, vehicle_type, spot_number, spot_status FROM spots";
		return jdbcTemplate.query(selectQuery, new SpotsMapper());
	}

	public void deleteUser(User user) {
		String deleteQuery = "UPDATE user SET status=0 WHERE email=?";
		Object[] params = { user.getEmail() };
		jdbcTemplate.update(deleteQuery, params);
	}

	public List<User> searchUser(String searchText) {
		String selectQuery = "SELECT user_id, user_name, user_password, phone_number, email " + "FROM user "
				+ "WHERE user_id = ? OR user_name LIKE ? OR phone_number LIKE ? OR email LIKE ?";
		Object[] params = { "%" + searchText + "%", "%" + searchText + "%", "%" + searchText + "%",
				"%" + searchText + "%" };
		return jdbcTemplate.query(selectQuery, new UserMapper(), params);
	}

	public List<Spots> searchSpots(String searchText) {
		String selectQuery = "SELECT user_id, spot_id, location, address_name, vehicle_type, spot_number, spot_status "
				+ "FROM spots "
				+ "WHERE user_id = ? OR spot_id=? OR location LIKE ? OR address_name LIKE ? OR vehicle_type LIKE ? OR spot_number LIKE ? OR spot_status LIKE ?";
		Object[] params = { searchText, searchText, "%" + searchText + "%", "%" + searchText + "%",
				"%" + searchText + "%", "%" + searchText + "%", "%" + searchText + "%" };
		return jdbcTemplate.query(selectQuery, new SpotsMapper(), params);
	}

	public List<Reservation> searchReservation(String searchText) {
		String selectQuery = "SELECT user_id, reservation_id, number_plate, start_date_time, end_date_time, fine_amount, reservation_status "
				+ "FROM reservation "
				+ "WHERE user_id = ? OR reservation_id = ? OR number_plate LIKE ? OR start_date_time = ? OR end_date_time = ? OR reservation_status = ?";
		Object[] params = { searchText, searchText, "%" + searchText + "%", searchText, searchText, searchText };
		return jdbcTemplate.query(selectQuery, new ReservationMapper(), params);
	}

	public List<Transaction> searchTransaction(String searchText) {
		String selectQuery = "SELECT user_id, transaction_id, reservation_id, price, payment_method, transaction_time, card_number, expiry_date, cvv, payment_status "
				+ "FROM transaction "
				+ "WHERE user_id = ? OR transaction_id = ? OR reservation_id = ? OR price = ? OR payment_method LIKE ? OR transaction_time = ? OR card_number = ? OR expiry_date = ? OR cvv = ? OR payment_status = ?";
		Object[] params = { searchText, searchText, searchText, searchText, "%" + searchText + "%", searchText,
				searchText, searchText, searchText, searchText };
		return jdbcTemplate.query(selectQuery, new TransactionMapper(), params);
	}

	public User readUsers(int id) {
		String selectQuery = "SELECT user_name, phone_number, email FROM user WHERE user_id = ?";
		try {
			Object[] params = { id };
			return jdbcTemplate.queryForObject(selectQuery, new PaymentMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void insertLocations(Locations locations) {
		String insertQuery = "INSERT INTO locations(location_id, location, location_image) VALUES (?,?,?)";
		Object[] params = { locations.getLocationId(), locations.getLocation(), locations.getLocationImage() };
		jdbcTemplate.update(insertQuery, params);
	}

	public List<Locations> readLocations() {
		String selectQuery = "SELECT location_id, location, location_image FROM locations";
		return jdbcTemplate.query(selectQuery, new LocationsMapper());
	}

	public Integer getLocationById(Locations locations) {
		String selectQuery = "SELECT location_id FROM locations WHERE location=?";
		Object[] details = { locations.getLocation() };
		return jdbcTemplate.queryForObject(selectQuery, Integer.class, details);
	}

	public void insertAddresses(Addresses addresses, int locationId, String address) {
		String insertQuery = "INSERT INTO addresses(address_id, location_id, address_name) VALUES (?,?,?)";
		Object[] params = { addresses.getAddressId(), locationId, address };
		jdbcTemplate.update(insertQuery, params);
	}

	public List<Addresses> readAddress(int locationId) {
		String selectQuery = "SELECT address_name FROM addresses where location_id=?";
		Object[] details = { locationId };
		return jdbcTemplate.query(selectQuery, new AddressesMapper(), details);
	}

	public List<Reservation> findReservationsToCalculateFine() {
		String selectQuery = "SELECT user_id, reservation_id, number_plate, start_date_time, end_date_time, fine_amount, reservation_status FROM reservation WHERE NOW() > end_date_time";
		return jdbcTemplate.query(selectQuery, new ReservationMapper());
	}

	public int calculateFine(Reservation reservation) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			LocalDateTime endDateTime = LocalDateTime.parse(reservation.getEndDateTime(), formatter);
			LocalDateTime currentDateTime = LocalDateTime.now();
			long hoursDifference = java.time.Duration.between(endDateTime, currentDateTime).toHours();
			int fineRatePerHour = 5;
			return (int) (hoursDifference * fineRatePerHour);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void updateFineAmount(int reservationId, int fineAmount) {
		String updateQuery = "UPDATE reservation SET fine_amount = ? WHERE reservation_id = ?";
		jdbcTemplate.update(updateQuery, fineAmount, reservationId);
	}

	public List<Reservation> findReservations(String startDateTime, String endDateTime) {
		String selectQuery = "SELECT user_id, reservation_id, number_plate, start_date_time, end_date_time, fine_amount, reservation_status FROM reservation "
				+ "WHERE start_date_time >= ? AND end_date_time <= ?";
		return jdbcTemplate.query(selectQuery, new ReservationMapper(), startDateTime, endDateTime);
	}

	public List<String> readSpotNumber(int id) {
		String selectQuery = "SELECT spot_number FROM spots WHERE user_id = ? AND spot_status = 'occupied'";
		return jdbcTemplate.queryForList(selectQuery, String.class, id);
	}

	public boolean alreadySelectedSpots(String spotNumber, String locationName) {
		String selectQuery = "SELECT spot_number FROM spots WHERE spot_number = ? AND location = ?";
		try {
			return jdbcTemplate.queryForObject(selectQuery, String.class, spotNumber, locationName) != null;
		} catch (Exception e) {
			return false;
		}

	}
}