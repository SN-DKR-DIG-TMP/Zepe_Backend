package com.zepebackend.bridge.microauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class Account {
//	@NonNull
	private String username;
//	@JsonInclude(Include.NON_NULL)
//	@EqualsAndHashCode.Exclude
	private String password;

	private String email;

	private Set<String> roles;

}
