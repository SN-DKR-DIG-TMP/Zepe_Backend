package com.zepebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashmentDto {
	// private Long idCollection;
	private String customer;
	private String cashier;
	private PartnerDto business;
	private PartnerDto trade;
	private PaymentTokenDto tokenPayment;
	private Double amount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private Date date;
	private String customerTokenFirebase;
	private String cashierFullName;
	private String customerName;
	private Boolean isValid;

}
