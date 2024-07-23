package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.parksmart.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setUserName(rs.getString("user_name"));
		user.setUserPassword(rs.getString("user_password"));
		user.setPhoneNumber(rs.getString("phone_number"));
		user.setEmail(rs.getString("email"));
		return user;

	}

}
