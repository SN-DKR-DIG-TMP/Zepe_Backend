package com.zepebackend.dto;

import com.zepebackend.bridge.microauth.dto.AuthentificationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnexionResponseDto {
	private AuthentificationResponse account;

	public ConnexionResponseDto(ConnexionResponseDto copy) {
		if (copy == null) {
			return;
		}

		setAccount(copy.getAccount());
	}
}
