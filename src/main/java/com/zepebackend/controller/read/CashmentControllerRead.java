package com.zepebackend.controller.read;

import static com.zepebackend.utils.ZepeConstants.SHORT_DATE;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.CashmentDto;
import com.zepebackend.dto.DateCashmentDto;
import com.zepebackend.service.CashmentService;

@RestController
@RequestMapping("/api/cashment")
@CrossOrigin("*")
public class CashmentControllerRead {
	final CashmentService cashmentService;

	public CashmentControllerRead(CashmentService cashmentService) {
		this.cashmentService = cashmentService;
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("/getCashmentByCashierAndDate/{id}/{date}")
	public List<CashmentDto> getAllCashmentByCashierAndDate(@PathVariable(value = "id") String cashier,
			@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		return cashmentService.findByCashierAndDate(cashier, new DateTime(date));

	}
	
	@GetMapping("/getCashmentByCustomerAndTwoDate")
	public List<CashmentDto> getCashmentByCustomerAndTwoDate(@RequestParam(value = "uid") String customer, 
			@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
		
		DateTime debut = new DateTime(start);
        DateTime startDay = debut.withTimeAtStartOfDay();

        DateTime endDay = new DateTime(end).withTime(23, 59, 59, 999);
		return cashmentService.findByCustomerAndTwoDate(customer, debut, endDay);

	}
	
	@GetMapping("/getCashmentByCustomer/{id}")
	public List<CashmentDto> getCashmentByCustomer(@PathVariable(value = "id") String customer) {
		return cashmentService.findByCustomer(customer);
	}

	@PreAuthorize("hasRole('MODERATOR')")
	@GetMapping("/getCashmentByTradeAndDate/{id}/{date}")
	public List<CashmentDto> getAllCashmentByTradeAndDate(@PathVariable(value = "id") String partner,
			@PathVariable(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		return cashmentService.findByTradeAndDate(partner, new DateTime(date));

	}

	@PreAuthorize("hasRole('MODERATOR')")
	@GetMapping("/getAllCashmentByTradeAndTwoDate")
	public List<CashmentDto> getAllCashmentByTradeAndTwoDate(@RequestParam(name = "idPartner") Long idPartner,
			@RequestParam(name = "start") @DateTimeFormat(pattern = SHORT_DATE) Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = SHORT_DATE) Date end) {
		DateCashmentDto dateCashmentDto = new DateCashmentDto(idPartner, start, end);
		return cashmentService.findByTradeAndTwoDate(dateCashmentDto);

	}
	
	@PreAuthorize("hasRole('MODERATOR')")
	@GetMapping("/getAllCashmentByEntrepriseAndTwoDate")
	public List<CashmentDto> getAllCashmentByEntrepriseAndTwoDate(@RequestParam(name = "idPartner") Long idPartner,
			@RequestParam(name = "start") @DateTimeFormat(pattern = SHORT_DATE) Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = SHORT_DATE) Date end) {
		DateCashmentDto dateCashmentDto = new DateCashmentDto(idPartner, start, end);
		return cashmentService.findByEntrepriseAndTwoDate(dateCashmentDto);

	}
}
