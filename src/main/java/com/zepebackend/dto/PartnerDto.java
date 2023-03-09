package com.zepebackend.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.zepebackend.utils.PartnerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDto {
	private String name;
	private String adress;
	private Long idPartner;
	@Enumerated(EnumType.STRING)
	private PartnerType partnerType;
}
