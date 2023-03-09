package com.zepebackend.bridge.microauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class AuthentificationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	private String zepeRole;
	private Long idPartner;
	private String partner;
	private boolean isFirstConnection;

}
