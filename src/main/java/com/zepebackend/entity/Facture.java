// BY MOMATH NDIAYE
package com.zepebackend.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zepebackend.utils.EtatFacture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facture implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFacture;
	@OneToOne
	@JoinColumn(name = "idPartenariat", referencedColumnName = "idPartenariat")
	private Partenariat partenariat;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date dateFacture;
	private String periode;
	private double montant;
	@Enumerated(EnumType.STRING)
	private EtatFacture etat = EtatFacture.A_PAYER;
}
