package com.zepebackend.bridge.microauth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DatasToUpdatePasswordDto {
	@NonNull
	private String oldPassword;
	@NonNull
	private String newPassword;
	@NonNull
	private String token;
}
