package com.chainsys.parksmart.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Spots {
	private int userId;
	private int spotId;
	private String location;
	private String addressName;
	private String vehicleType;
	private String spotNumber;
	private int countSpotNumber;
	private String spotStatus;

	public Spots(int userId, int spotId, String location, String addressName, String vehicleType, String spotNumber,
			int countSpotNumber, String spotStatus) {
		super();
		this.userId = userId;
		this.spotId = spotId;
		this.location = location;
		this.addressName = addressName;
		this.vehicleType = vehicleType;
		this.spotNumber = spotNumber;
		this.countSpotNumber = countSpotNumber;
		this.spotStatus = spotStatus;
	}

	@Override
	public String toString() {
		return "Spots [userId=" + userId + ", spotId=" + spotId + ", location=" + location + ", address_name="
				+ addressName + ", vehicleType=" + vehicleType + ", spotNumber=" + spotNumber + ", countSpotNumber="
				+ countSpotNumber + ", spotStatus=" + spotStatus + ", getUserId()=" + getUserId() + ", getSpotId()="
				+ getSpotId() + ", getLocation()=" + getLocation() + ", getAddressName()=" + getAddressName()
				+ ", getVehicleType()=" + getVehicleType() + ", getSpotNumber()=" + getSpotNumber()
				+ ", getCountSpotNumber()=" + getCountSpotNumber() + ", getSpotStatus()=" + getSpotStatus() + "]";
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getSpotNumber() {
		return spotNumber;
	}

	public void setSpotNumber(String spotNumber) {
		this.spotNumber = spotNumber;
	}

	public int getCountSpotNumber() {
		return countSpotNumber;
	}

	public void setCountSpotNumber(int countSpotNumber) {
		this.countSpotNumber = countSpotNumber;
	}

	public String getSpotStatus() {
		return spotStatus;
	}

	public void setSpotStatus(String spotStatus) {
		this.spotStatus = spotStatus;
	}

	@Autowired
	public Spots() {
	}

}
