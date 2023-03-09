// BY MOMATH NDIAYE
package com.zepebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Configuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Configuration implements Serializable {
	@EmbeddedId
	ConfigurationId configurationId;
	private int valeur;
	private String strValeur;
}
