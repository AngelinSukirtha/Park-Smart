package com.chainsys.parksmart.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.chainsys.parksmart.model.Locations;

@Repository
public class LocationsMapper implements RowMapper<Locations> {

	@Override
	public Locations mapRow(ResultSet rs, int rowNum) throws SQLException {
		Locations locations = new Locations();
		locations.setLocationId(rs.getInt("location_id"));
		locations.setLocation(rs.getString("location"));
		Blob blob = rs.getBlob("location_image");
		if (blob != null) {
			int blobLength = (int) blob.length();
			byte[] blobAsBytes = blob.getBytes(1, blobLength);
			locations.setLocationImage(blobAsBytes);
		}
		return locations;
	}

}
