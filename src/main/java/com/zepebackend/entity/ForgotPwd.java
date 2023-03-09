package com.zepebackend.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPwd {
	@Id
	@GeneratedValue
	private int id_req;
		private static final long serialVersionUID = 1L;
	
	private String id_user;
	private Long id_partner;
	private Boolean treated;
	private Date date;
	public ForgotPwd(String id_user, Long id_partner, Boolean treated, Date date) {
		super();
		this.treated = treated;
		this.id_user = id_user;
		this.id_partner = id_partner;
		this.date = date;
		
	}

}
