package com.zepebackend.dto.input;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureRequest {

	private long idPartner;
	private Date start;
	private Date end;
}
