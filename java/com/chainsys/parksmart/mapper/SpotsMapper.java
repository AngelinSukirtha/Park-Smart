package com.chainsys.parksmart.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chainsys.parksmart.model.Spots;

@Repository
public class SpotsMapper implements RowMapper<Spots> {

	@Override
	public Spots mapRow(ResultSet rs, int rowNum) throws SQLException {
		Spots spots = new Spots();
		spots.setUserId(rs.getInt("user_id"));
		spots.setSpotId(rs.getInt("spot_id"));
		spots.setLocation(rs.getString("location"));
		spots.setAddressName(rs.getString("address_name"));
		spots.setVehicleType(rs.getString("vehicle_type"));
		spots.setSpotNumber(rs.getString("spot_number"));
		spots.setSpotStatus(rs.getString("spot_status"));
		return spots;
	}

}
