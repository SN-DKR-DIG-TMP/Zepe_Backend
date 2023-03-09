package com.zepebackend.bridge.microauth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class SignupRequest {
	private String username;

	private String email;

	private List<String> role;

	private String password;

}
