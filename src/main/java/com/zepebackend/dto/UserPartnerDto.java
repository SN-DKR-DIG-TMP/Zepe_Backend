package com.zepebackend.dto;

import com.zepebackend.entity.UserPartnerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPartnerDto {
	UserPartnerId userPartnerId;
	String role;

}
