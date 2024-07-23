package com.chainsys.parksmart.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.chainsys.parksmart.dao.UserDAO;
import com.chainsys.parksmart.dao.UserImpl;
import com.chainsys.parksmart.model.Addresses;
import com.chainsys.parksmart.model.Spots;
import com.chainsys.parksmart.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Controller
public class SpotsController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	Spots spots;

	@Autowired
	Validation validation;

	@Autowired
	UserImpl userImpl;

	@GetMapping("/locationName")
	public String handleLocations(Model model, @RequestParam("locationId") int locationId, HttpSession session) {
		session.setAttribute("locationId", locationId);
		List<Addresses> list = userImpl.readAddress(locationId);
		model.addAttribute("list", list);
		return "address";
	}

	@GetMapping("/address")
	public String address(@RequestParam("address") String address, Model model, HttpSession session) {
		spots.setAddressName(address);
		session.setAttribute("address", address);
		int locationId = (int) session.getAttribute("locationId");
		String locationName = userImpl.getLocationByLocationId(locationId);
		spots.setLocation(locationName);
		List<String> spotList = userDAO.readSpotNumbers(locationName);
		session.setAttribute("spotList", spotList);
		return "spots";
	}

	@GetMapping("/spots")
	public String selectedSpots(HttpSession session, @RequestParam("selectedSpots") String[] selectedSpots,
			Model model) {
		int userId = (int) session.getAttribute("userId");
		int locationId = (int) session.getAttribute("locationId");
		String locationName = userImpl.getLocationByLocationId(locationId);
		spots.setLocation(locationName);
		String address = (String) session.getAttribute("address");

		long validSpotCount = Arrays.stream(selectedSpots)
				.map(spotNumber -> spotNumber.substring(1, spotNumber.length() - 1))
				.flatMap(spotString -> Arrays.stream(spotString.split("\",\"")))
				.map(spot -> spot.trim().replace("\"", "")).peek(spot -> {
					String vehicleType = switch (spot.charAt(0)) {
					case 'C' -> "Car";
					case 'B' -> "Bike";
					case 'T' -> "Truck";
					default -> null;
					};

					spots.setVehicleType(vehicleType);
					session.setAttribute("vehicleType", vehicleType);
					if (!userImpl.alreadySelectedSpots(spot, locationName)) {
						userDAO.insertSpots(spots, userId, locationName, address, vehicleType, spot);
					}

				}).count();
		spots.setCountSpotNumber((int) validSpotCount);
		session.setAttribute("countSpotNumber", validSpotCount);

		return "reservation";
	}

	@GetMapping("address1")
	public String showAddAddress1(Model model, HttpSession session) {
		int locationId = (int) session.getAttribute("locationId");
		String locationName = userImpl.getLocationByLocationId(locationId);
		spots.setLocation(locationName);
		List<String> spotList = userDAO.readSpotNumbers(locationName);
		session.setAttribute("spotList", spotList);
		return "spots";
	}
}
