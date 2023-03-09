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
public class UpdateRoleRequest {
	private String username;
	private List<String> role;

}
