package com.zepebackend.bridge.microuser.dto;

import java.util.Date;

import com.zepebackend.dto.ForgotPwdRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPwdBadRequestDto {
	String  id_user;
	String ip_address;
	Date date;
}
