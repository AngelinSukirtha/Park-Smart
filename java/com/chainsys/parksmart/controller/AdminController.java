package com.chainsys.parksmart.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chainsys.parksmart.dao.UserDAO;
import com.chainsys.parksmart.dao.UserImpl;
import com.chainsys.parksmart.model.Addresses;
import com.chainsys.parksmart.model.Locations;
import com.chainsys.parksmart.model.Reservation;
import com.chainsys.parksmart.model.Spots;
import com.chainsys.parksmart.model.Transaction;
import com.chainsys.parksmart.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@Autowired
	Spots spots;

	@Autowired
	Reservation reservation;

	@Autowired
	UserImpl userImpl;

	@RequestMapping("/admin")
	public String adminHomePage() {
		return "admin";
	}

	@RequestMapping("/addLocation")
	public String adminAddLocation(Model model, Locations location) {
		model.addAttribute("location", new Locations());
		return "addLocation";
	}

	@GetMapping("/users")
	public String handleUsers(HttpSession session, Model model) {
		List<User> list = userDAO.readUser();
		model.addAttribute("list", list);
		return "userManagement";
	}

	@GetMapping("/deleteUser")
	public String deleteUser(@RequestParam("email") String email, Model model) {
		user.setEmail(email);
		userDAO.deleteUser(user);
		List<User> list = userDAO.readUser();
		model.addAttribute("list", list);
		return "userManagement";

	}

	@GetMapping("/searchUser")
	public String searchUser(@RequestParam("searchText") String searchText, Model model) {
		List<User> list = userDAO.searchUser(searchText);
		model.addAttribute("list", list);
		return "userManagement";
	}

	@GetMapping("/manageSpots")
	public String handleSpots(Model model) {
		List<Spots> list = userDAO.readSpotsAdmin();
		model.addAttribute("list", list);
		return "spotsManagement";
	}

	@GetMapping("/updateSpotStatus")
	public String updateSpots(Model model, @RequestParam("spotId") int spotId,
			@RequestParam("spotStatus") String spotStatus) {
		spots.setSpotId(spotId);
		spots.setSpotStatus(spotStatus);
		userDAO.updateSpotStatus(spots);
		List<Spots> list = userDAO.readSpotsAdmin();
		model.addAttribute("list", list);
		return "spotsManagement";
	}

	@GetMapping("/searchSpots")
	public String searchSpots(@RequestParam("searchText") String searchText, Model model) {
		List<Spots> list = userDAO.searchSpots(searchText);
		model.addAttribute("list", list);
		return "spotsManagement";
	}

	@GetMapping("/manageReservations")
	public String handleReservations(Model model) {
		List<Reservation> reservations = userImpl.findReservationsToCalculateFine();

		int reservationId = reservation.getReservationId();
		reservation.setReservationId(reservationId);

		for (Reservation reservation : reservations) {
			Integer fineAmount = userImpl.calculateFine(reservation);

			userImpl.updateFineAmount(reservation.getReservationId(), fineAmount);
		}
		List<Reservation> list = userDAO.readReservations();
		model.addAttribute("list", list);
		return "reservationManagement";
	}

	@GetMapping("/searchReservation")
	public String searchReservation(@RequestParam("searchText") String searchText, Model model) {
		List<Reservation> list = userDAO.searchReservation(searchText);
		model.addAttribute("list", list);
		return "reservationManagement";
	}

	@GetMapping("/filterReservation")
	public String filterReservation(@RequestParam("startDateTime") String startDateTime,
			@RequestParam("endDateTime") String endDateTime, Model model) {
		List<Reservation> list = userImpl.findReservations(startDateTime, endDateTime);
		model.addAttribute("list", list);
		return "reservationManagement";
	}

	@GetMapping("/updateReservationStatus")
	public String updateReservationStatus(Model model, @RequestParam("reservationId") int reservationId,
			@RequestParam("reservationStatus") String reservationStatus) {
		reservation.setReservationId(reservationId);
		reservation.setReservationStatus(reservationStatus);
		userDAO.updateReservationStatus(reservation);
		List<Reservation> list = userDAO.readReservations();
		model.addAttribute("list", list);
		return "reservationManagement";
	}

	@GetMapping("/manageTransactions")
	public String handleTransactions(Model model) {
		List<Transaction> list = userDAO.readTransaction();
		model.addAttribute("list", list);
		return "transactionManagement";
	}

	@GetMapping("/searchTransaction")
	public String searchTransaction(@RequestParam("searchText") String searchText, Model model) {
		List<Transaction> list = userDAO.searchTransaction(searchText);
		model.addAttribute("list", list);
		return "transactionManagement";
	}

	@PostMapping("/addLocations")
	public String addLocations(@RequestParam("image") MultipartFile imageFile,
			@RequestParam("location") String location, Model model) throws IOException {
		if (!imageFile.isEmpty()) {
			byte[] imageBytes = imageFile.getBytes();
			Locations locations = new Locations();
			locations.setLocationImage(imageBytes);
			locations.setLocation(location);
			userImpl.insertLocations(locations);
			List<Locations> list = userImpl.readLocations();
			for (Locations loc : list) {
				String base64Image = Base64.getEncoder().encodeToString(loc.getLocationImage());
				loc.setBase64Image(base64Image);
			}
			model.addAttribute("list", list);
		}
		return "adminLocation";
	}

	@GetMapping("/manageLocations")
	public String handleLocations(Model model) {
		List<Locations> list = userImpl.readLocations();
		for (Locations loc : list) {
			String base64Image = Base64.getEncoder().encodeToString(loc.getLocationImage());
			loc.setBase64Image(base64Image);
		}
		model.addAttribute("list", list);
		return "adminLocation";
	}

	@GetMapping("/manageAddress")
	public String handleAddress(Model model, @RequestParam("locationId") int locationId, HttpSession session) {
		session.setAttribute("locationId", locationId);
		List<Addresses> list = userImpl.readAddress(locationId);
		model.addAttribute("list", list);
		return "adminAddress";
	}

	@GetMapping("/addAddress")
	public String addAddress(Model model, HttpSession session, @RequestParam("address") String address) {
		int locationId = (int) session.getAttribute("locationId");
		Addresses addresses = new Addresses();
		userImpl.insertAddresses(addresses, locationId, address);
		List<Addresses> list = userImpl.readAddress(locationId);
		model.addAttribute("list", list);
		return "adminAddress";
	}

	@RequestMapping("/adminAddAddress")
	public String showAddAddress() {
		return "addAddress.html";
	}

}