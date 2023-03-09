package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
	String account;
	Date date;
	PartnerDto trade;
	PartnerDto business;
	String customerFullName;

}
