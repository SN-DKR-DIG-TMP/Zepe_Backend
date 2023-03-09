package com.zepebackend.dto.output;

import java.io.Serializable;
import java.util.List;

import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.dto.PartnerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminMediationDTO extends ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public AdminMediationDTO(ConnexionResponseDto connexion) {
		super(connexion);
	}

	protected List<PartenariatDto> partenariats;
	protected List<Admin> userAdmin;
	protected List<PartnerDto> partners;
	protected List<AffRoleDto> affRoles;

}
