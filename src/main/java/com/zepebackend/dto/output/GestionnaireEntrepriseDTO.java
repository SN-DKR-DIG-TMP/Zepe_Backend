package com.zepebackend.dto.output;

import java.io.Serializable;
import java.util.List;

import com.zepebackend.agg.AggPayment;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.FactureDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GestionnaireEntrepriseDTO extends ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public GestionnaireEntrepriseDTO(ConnexionResponseDto connexion) {
		super(connexion);
	}

	protected List<FactureDto> factures;
	protected List<AggPayment> payments;
}
