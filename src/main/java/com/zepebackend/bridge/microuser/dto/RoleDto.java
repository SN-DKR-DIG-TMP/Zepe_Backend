package com.zepebackend.bridge.microuser.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Data
public class RoleDto {
	private String name;
	private ArrayList<PermissionDto> permissions = new ArrayList<PermissionDto>();

	public RoleDto(String name, PermissionDto permission) {
		this.name = name;
		this.permissions.add(permission);
	}

	public boolean add(PermissionDto permissionDto) {
		return this.permissions.add(permissionDto);
	}

}
