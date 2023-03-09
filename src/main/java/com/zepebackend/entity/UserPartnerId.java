package com.zepebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPartnerId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idUser;
	@ManyToOne
	@JoinColumn(referencedColumnName = "idPartner")
	private Partner partner;

}
