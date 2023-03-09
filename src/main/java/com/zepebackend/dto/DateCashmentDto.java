package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateCashmentDto implements Serializable {
	private Long idCommerce;
	private Date dateDebut;
	private Date dateFin;
}
