package com.zepebackend.dto;

import java.util.Date;

import com.zepebackend.entity.ForgotPwd;
import com.zepebackend.entity.UserPartnerId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPwdRequestDto {
	Long id_partner;
	String  id_user;
	Boolean treated;
	Date date;
}
