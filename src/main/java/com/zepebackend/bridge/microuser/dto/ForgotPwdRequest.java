package com.zepebackend.bridge.microuser.dto;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForgotPwdRequest {
	@NonNull
	private String id_user;
	@NonNull
	private Long id_partner;
	private Boolean treated = false;
	private Date date = new Date();
}
