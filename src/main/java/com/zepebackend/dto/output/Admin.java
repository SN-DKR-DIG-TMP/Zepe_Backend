package com.zepebackend.dto.output;

import com.zepebackend.bridge.microuser.dto.UserStatus;
import com.zepebackend.dto.PartnerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private PartnerDto partner;
	private String role;
	private UserStatus status;

}
