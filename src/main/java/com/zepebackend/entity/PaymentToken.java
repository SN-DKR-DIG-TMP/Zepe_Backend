package com.zepebackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payment_token")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentToken {
	public enum Status {
		USED, UNUSED

	}
	@Id
	private String token;
	private String userMatricule;
	private Status status = Status.UNUSED;
}
