package com.zepebackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Data
@NoArgsConstructor
public class Payment {
	String account;
	String customerFullName;
	Date date;
	@ManyToOne
	@JoinColumn(name = "trade", referencedColumnName = "idPartner")
	Partner trade;
	@ManyToOne
	@JoinColumn(name = "business", referencedColumnName = "idPartner")
	Partner business;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPayment;

	public Payment(String account, Date date, Partner trade, Partner business, String customerFullName) {
		super();
		this.account = account;
		this.date = date;
		this.trade = trade;
		this.customerFullName = customerFullName;
		this.business = business;
	}

}
