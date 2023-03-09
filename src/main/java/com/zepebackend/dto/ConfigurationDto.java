// BY MOMATH NDIAYE
package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto implements Serializable {
	protected String cle;
	protected int valeur;
	protected String strValeur;
	protected PartnerDto partner;

}
