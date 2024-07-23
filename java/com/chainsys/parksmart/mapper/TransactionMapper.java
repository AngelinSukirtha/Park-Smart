package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.chainsys.parksmart.model.Transaction;

@Repository
public class TransactionMapper implements RowMapper<Transaction> {

	@Override
	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setUserId(rs.getInt("user_id"));
		transaction.setReservationId(rs.getInt("reservation_id"));
		transaction.setTransactionId(rs.getInt("transaction_id"));
		transaction.setPrice(rs.getInt("price"));
		transaction.setPaymentMethod(rs.getString("payment_method"));
		transaction.setTransactionTime(rs.getString("transaction_time"));
		transaction.setCardNumber(rs.getString("card_number"));
		transaction.setExpiryDate(rs.getString("expiry_date"));
		transaction.setCvv(rs.getString("cvv"));
		transaction.setPaymentStatus(rs.getString("payment_status"));
		return transaction;
		
	}

}
