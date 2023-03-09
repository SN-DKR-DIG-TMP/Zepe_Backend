package com.zepebackend.dto.output;

import java.util.List;

import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.PartenariatDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminCommerceDTO extends ClientDTO {
	public AdminCommerceDTO(ConnexionResponseDto connexion) {
		super(connexion);
	}

	protected List<User> userList;
	protected List<PartenariatDto> parteneriatDTOList;
	protected AffRoleDto affRole;
}
