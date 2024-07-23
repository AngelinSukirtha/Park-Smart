package com.chainsys.parksmart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.parksmart.model.Reservation;
import com.chainsys.parksmart.model.Spots;
import com.chainsys.parksmart.model.Transaction;
import com.chainsys.parksmart.model.User;

@Repository
public interface UserDAO {
	public boolean userRegistration(User user);

	public boolean userLogin(User user);

	public Integer getUserById(User user);

	public void insertSpots(Spots spots, int id, String locationName, String address, String vehicleType,
			String[] strArr);

	public List<String> readSpotNumbers(String locationName);

	public int countSpotNumber(Spots spots, int id);

	public void insertReservation(Reservation reservation, int id);

	public List<Reservation> readReservation();

	public void updateReservationStatus(Reservation reservation);

	public void updateIsActive(Reservation reservation);

	public List<Reservation> readReservation(Reservation reservation);

	public int getPrice(Transaction transaction, String vehicleType, String startDateTime, String endDateTime);

	public String getCurrentTransactionTimeFormatted(Transaction transaction);

	public void insertTransaction(int reservationId, int id, int price, String transactionTime);

	public void updateTransaction(Transaction transaction, int reservationId);

	public void addPaymentMethod(Transaction transaction, int id, String paymentMethod);

	public List<Transaction> readTransaction();

	public Transaction readTransactions(Transaction transaction);

	public int getReservationByReservationId(int id);

	public List<User> readUser();

	public void deleteUser(User user);

	public List<User> searchUser(String searchText);

	public List<Spots> readSpotsAdmin();

	public void updateSpotStatus(Spots spots);

	public List<Spots> searchSpots(String searchText);

	public List<Reservation> readReservations();

	public List<Reservation> searchReservation(String searchText);

	public List<Transaction> searchTransaction(String searchText);

	public User readUsers(int id);

}
