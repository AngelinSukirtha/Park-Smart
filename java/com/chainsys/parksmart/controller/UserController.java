package com.chainsys.parksmart.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.parksmart.dao.UserDAO;
import com.chainsys.parksmart.dao.UserImpl;
import com.chainsys.parksmart.model.Locations;
import com.chainsys.parksmart.model.User;
import com.chainsys.parksmart.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@Autowired
	Validation validation;

	@Autowired
	UserImpl userImpl;

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/about")
	public String about() {
		return "aboutUs";
	}

	@RequestMapping("/price")
	public String price() {
		return "priceDetails";
	}

	@RequestMapping("/userLogin")
	public String showUserLoginPage() {
		return "userLogin";
	}

	@RequestMapping("/userRegister")
	public String showUserRegisterPage() {
		return "userRegister";
	}

	@PostMapping("/register")
	public String saveUser(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("email") String email, Model model) {

		if (!validation.validateUserName(userName) || !validation.validateUserPassword(userPassword)
				|| !validation.validatePhoneNumber(phoneNumber) || !validation.validateEmail(email)) {
			return "userRegister";
		}
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		boolean isUserRegistered = userDAO.userRegistration(user);
		if (!isUserRegistered) {
			model.addAttribute("registrationError", "User with these details already exists.");
			return "userRegister";
		}
		return "userLogin";
	}

	@PostMapping("/login")
	public String userLogin(@RequestParam("email") String email, HttpSession session,
			@RequestParam("userPassword") String userPassword, Model model) {

		if (!validation.validateEmail(email) || !validation.validateUserPassword(userPassword)) {
			return "userLogin";
		}
		user.setEmail(email);

		if (email.equals("angelin@parkingspot.com") && userPassword.equals("Angelin1")) {
			return "admin";
		} else {
			boolean loginSuccessful = userDAO.userLogin(user);
			if (loginSuccessful) {
				int userId = userDAO.getUserById(user);
				session.setAttribute("userId", userId);
				List<Locations> list = userImpl.readLocations();
				for (Locations location : list) {
					String base64Image = Base64.getEncoder().encodeToString(location.getLocationImage());
					location.setBase64Image(base64Image);
				}
				model.addAttribute("list", list);
				return "location";
			} else {
				return "userRegister";
			}
		}
	}

}