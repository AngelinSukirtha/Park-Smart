package com.chainsys.parksmart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.parksmart.dao.UserDAO;
import com.chainsys.parksmart.dao.UserImpl;
import com.chainsys.parksmart.model.Spots;
import com.chainsys.parksmart.model.Transaction;
import com.chainsys.parksmart.model.User;
import com.chainsys.parksmart.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@Autowired
	Transaction transaction;

	@Autowired
	Validation validation;

	@Autowired
	Spots spots;

	@Autowired
	UserImpl userImpl;

	@GetMapping("/userTransaction")
	public String handleTransaction(HttpSession session, Model model) {
		int id = (int) session.getAttribute("userId");

		String vehicleType = (String) session.getAttribute("vehicleType");

		int reservationId = (int) session.getAttribute("reservationId");

		String startDateTime = (String) session.getAttribute("startDateTime");
		String endDateTime = (String) session.getAttribute("endDateTime");

		if (!validation.validateVehicleType(vehicleType)) {
			return "transaction";
		}

		userDAO.getPrice(transaction, vehicleType, startDateTime, endDateTime);
		int price = transaction.getPrice();

		userDAO.getCurrentTransactionTimeFormatted(transaction);
		String transactionTime = transaction.getTransactionTime();

		model.addAttribute("price", price);
		model.addAttribute("transactionTime", transactionTime);

		userDAO.insertTransaction(reservationId, id, price, transactionTime);
		userDAO.readTransactions(transaction);
		return "transaction";
	}

	@GetMapping("/pay")
	public String handlePay(HttpSession session, @RequestParam("paymentMethod") String paymentMethod) {
		transaction.setPaymentMethod(paymentMethod);

		if (!validation.validatePaymentMethod(paymentMethod)) {
			return "transaction";
		}

		int id = (int) session.getAttribute("userId");
		userDAO.addPaymentMethod(transaction, id, paymentMethod);
		return "payment";
	}

	@GetMapping("/payment")
	public String handlePayment(HttpSession session, Model model, @RequestParam("cardNumber") String cardNumber,
			@RequestParam("expiryDate") String expiryDate, @RequestParam("cvv") String cvv) {
		int id = (int) session.getAttribute("userId");
		transaction.setCardNumber(cardNumber);
		transaction.setExpiryDate(expiryDate);
		transaction.setCvv(cvv);

		int reservationId = (int) session.getAttribute("reservationId");

		if (!validation.validateCardNumber(cardNumber) || !validation.validateExpiryDate(expiryDate)
				|| !validation.validateCvv(cvv)) {
			return "payment";
		}

		userDAO.updateTransaction(transaction, reservationId);
		userDAO.readTransactions(transaction);
		List<String> spotNumbers = userImpl.readSpotNumber(id);
		session.setAttribute("spotNumbers", spotNumbers);
		List<Integer> spotId = (List<Integer>) session.getAttribute("spotId");
		userImpl.updateSpotStatus(spotId);
		userImpl.updateReservation(reservationId);

		return "redirect:/userTransactionConfirmation";
	}

	@GetMapping("/userTransactionConfirmation")
	public String handleTransactionConfirmation(HttpSession session, Model model) {
		int id = (int) session.getAttribute("userId");
		userDAO.readTransactions(transaction);
		int price = transaction.getPrice();
		String transactionTime = transaction.getTransactionTime();
		User user = userDAO.readUsers(id);
		String userName = user.getUserName();
		String phoneNumber = user.getPhoneNumber();
		String email = user.getEmail();
		String[] strArr = (String[]) session.getAttribute("strArr");
		List<String> spotNumbers = new ArrayList<>();
		for (String s : strArr) {
			spotNumbers.add(s);
		}
		model.addAttribute("userName", userName);
		model.addAttribute("phoneNumber", phoneNumber);
		model.addAttribute("email", email);
		model.addAttribute("spotNumbers", spotNumbers);
		model.addAttribute("price", price);
		model.addAttribute("transactionTime", transactionTime);

		model.addAttribute("paymentSuccessMessage", "Payment successful!");
		model.addAttribute("paymentSuccessScript", true);

		return "transactionConfirmation";
	}

}
