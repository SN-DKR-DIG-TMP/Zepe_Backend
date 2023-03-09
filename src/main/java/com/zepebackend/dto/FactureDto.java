// BY MOMATH NDIAYE
package com.zepebackend.dto;

import java.io.Serializable;
import java.util.Date;

import com.zepebackend.utils.EtatFacture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureDto implements Serializable {

	private Long idFacture;
	private PartenariatDto partenariat;
	private Date dateFacture;
	private String periode;
	private double montant;
	private EtatFacture etat = EtatFacture.A_PAYER;
}
