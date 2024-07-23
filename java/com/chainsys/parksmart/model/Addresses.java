package com.chainsys.parksmart.model;

public class Addresses {
	private int addressId;
	private int locationId;
	private String addressName;

	public Addresses(int addressId, int locationId, String addressName) {
		super();
		this.addressId = addressId;
		this.locationId = locationId;
		this.addressName = addressName;
	}

	public Addresses() {
	}

	@Override
	public String toString() {
		return "Addresses [addressId=" + addressId + ", locationId=" + locationId + ", addressName=" + addressName
				+ ", getAddressId()=" + getAddressId() + ", getLocationId()=" + getLocationId() + ", getAddressName()="
				+ getAddressName() + "]";
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

}
