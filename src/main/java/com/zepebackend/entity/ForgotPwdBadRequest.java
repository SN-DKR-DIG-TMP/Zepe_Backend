package com.zepebackend.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPwdBadRequest {

	@Id
	@GeneratedValue
	private int id_req;
		private static final long serialVersionUID = 1L;
		
		String  id_user;
		String ip_address;
		Date date;
		public ForgotPwdBadRequest(String id_user, String ip_address, Date date) {
			super();
			this.id_user = id_user;
			this.ip_address = ip_address;
			this.date = date;
		}
		
}
