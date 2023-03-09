package com.zepebackend.dto.output;

import com.zepebackend.dto.CashmentDto;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.FactureDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GestionnaireCommerceDTO extends ConnexionResponseDto implements Serializable {
	public GestionnaireCommerceDTO(ConnexionResponseDto connexion) {
		super(connexion);
	}

	private List<FactureDto> factures;
	private List<CashmentDto> cashments;

}
