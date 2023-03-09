package com.zepebackend.controller.read;

import static com.zepebackend.utils.ZepeConstants.SHORT_DATE;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.agg.AggPayment;
import com.zepebackend.dto.PaymentDto;
import com.zepebackend.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentControllerRead {
	final PaymentService paymentService;

	public PaymentControllerRead(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/getAll")
	public List<PaymentDto> getAllPartner() {
		return paymentService.getAllPayment();
	}

	@GetMapping("/getPaymentByDateAndUser/{id}/{date}")
	public List<PaymentDto> getAllPaymentByDateAndUser(@PathVariable(value = "id") String account,
			@PathVariable(value = "date") @DateTimeFormat(pattern = SHORT_DATE) Date date) throws ParseException {
		return paymentService.getAllPaymentByDateAndUser(account, new DateTime(date));
	}
	
	@GetMapping("/getPaymentByUser/{id}")
	public List<PaymentDto> getAllPaymentUser(@PathVariable(value = "id") String account
			) throws ParseException {
		return paymentService.getAllPaymentUser(account);
	}

	@PreAuthorize("hasRole('MODERATOR')")
	@GetMapping("/getPaymentByDateAndBusiness/{id}/{date}")
	public Set<PaymentDto> getAllPaymentByDateAndPartner(@PathVariable(value = "id") String partner,
			@PathVariable(value = "date") @DateTimeFormat(pattern = SHORT_DATE) Date date) throws ParseException {
		return paymentService.getAllPaymentByDateAndBusiness(partner, date);
	}

	@GetMapping("/getPaymentByBusinessAndDate")
	public List<AggPayment> getPaymentByBusinessAndDate(@RequestParam(name = "idPartner") Long idPartner,
			@RequestParam(name = "start") @DateTimeFormat(pattern = SHORT_DATE) Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = SHORT_DATE) Date end) throws ParseException {
		return paymentService.getPaymentByBusinessAndDates(idPartner, start, end);
	}

}
