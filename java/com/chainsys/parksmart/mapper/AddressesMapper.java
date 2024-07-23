package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.parksmart.model.Addresses;

public class AddressesMapper implements RowMapper<Addresses> {

	@Override
	public Addresses mapRow(ResultSet rs, int rowNum) throws SQLException {
		Addresses addresses = new Addresses();
		addresses.setAddressName(rs.getString("address_name"));
		return addresses;
	}

}
