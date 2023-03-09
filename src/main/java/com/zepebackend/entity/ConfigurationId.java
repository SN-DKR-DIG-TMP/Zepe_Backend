package com.zepebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cle;
	@OneToOne
	@JoinColumn(name = "idPartner", referencedColumnName = "idPartner")
	private Partner partner;

}
