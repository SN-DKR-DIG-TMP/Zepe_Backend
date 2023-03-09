package com.zepebackend.controller.write;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.zepebackend.dto.CashmentDto;
import com.zepebackend.service.CashmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cashment")
public class CashmentControllerWrite {
	final CashmentService cashmentService;

	public CashmentControllerWrite(CashmentService cashmentService) {
		this.cashmentService = cashmentService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveCashment(@RequestBody CashmentDto cashmentDto) {
		return cashmentService.recordCashment(cashmentDto);
	}
	
	@PostMapping("/unvalid")
	public ResponseEntity<String> unvalidCashment(@RequestBody CashmentDto cashmentDto) {
		return cashmentService.unvalidCashment(cashmentDto);
	}

}
