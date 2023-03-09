package com.zepebackend.dto.output;

import java.util.List;

import com.zepebackend.utils.PartnerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AffRoleDto {

	private PartnerType partnerType;
	private List<String> roles;
}
