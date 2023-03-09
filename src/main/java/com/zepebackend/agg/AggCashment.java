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
public class AggCashment {
	private double cashmentAmount;

	private String businessName;

	private Long businessId;

	private Date startDate;

	private Date endDate;

}
