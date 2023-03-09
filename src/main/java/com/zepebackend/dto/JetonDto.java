// BY MOMATH NDIAYE
package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JetonDto implements Serializable {
	private Long idJeton;
	private double montantEmploye;
	private double montantEntreprise;
	private PartnerDto partner;
}
