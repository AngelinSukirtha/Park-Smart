package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.parksmart.model.User;

public class PaymentMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserName(rs.getString("user_name"));
		user.setPhoneNumber(rs.getString("phone_number"));
		user.setEmail(rs.getString("email"));
		return user;

	}

}
