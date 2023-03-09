package com.zepebackend.bridge.microuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount {
	@NonNull
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private String partner;
	private UserStatus status;
	private String role;

}
