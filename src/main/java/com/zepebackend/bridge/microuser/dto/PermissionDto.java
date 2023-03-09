package com.zepebackend.bridge.microuser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PermissionDto {
	private String name;

	@JsonInclude(Include.NON_NULL)
	private List<String> members;

	public boolean add(String member) {
		if (this.members == null)
			members = new ArrayList<String>();

		return members.add(member);
	}

}
