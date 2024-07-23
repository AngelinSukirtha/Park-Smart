package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chainsys.parksmart.model.Reservation;

@Repository
public class ReservationMapper implements RowMapper<Reservation> {

	@Override
	public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reservation reservation = new Reservation();
		reservation.setUserId(rs.getInt("user_id"));
		reservation.setReservationId(rs.getInt("reservation_id"));
		reservation.setNumberPlate(rs.getString("number_plate"));
		reservation.setStartDateTime(rs.getString("start_date_time"));
		reservation.setEndDateTime(rs.getString("end_date_time"));
		reservation.setFineAmount(rs.getInt("fine_amount"));
		reservation.setReservationStatus(rs.getString("reservation_status"));
		return reservation;

	}
}
