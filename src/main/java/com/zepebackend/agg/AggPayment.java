package com.zepebackend.agg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AggPayment {

	private String account;

	private int tickets;
	private Double employeePart;
	private Double entreprisePart;

	private Date startDate;

	private Date endDate;
	private String customerFullName;

}
