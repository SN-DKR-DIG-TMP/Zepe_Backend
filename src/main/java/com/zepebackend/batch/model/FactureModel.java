// BY MOMATH NDIAYE

package com.zepebackend.batch.model;

import java.util.Date;

import com.zepebackend.utils.EtatFacture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureModel {
	private double montant;
	private Long tradeId;
	private Long businessId;
	private Long partenariatId;
	private EtatFacture etat = EtatFacture.A_PAYER;
	private Date dateFacture;
	private String periode;
}
