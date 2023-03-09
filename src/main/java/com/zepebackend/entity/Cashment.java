package com.zepebackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cashment")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Cashment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCollection;
	private String customer;
	private String cashier;
	private String cashierFullName;
	private Boolean isValid;
	
	@ManyToOne
	@JoinColumn(name = "business", referencedColumnName = "idPartner")
	private Partner business;
	@ManyToOne
	@JoinColumn(name = "trade", referencedColumnName = "idPartner")
	private Partner trade;
	@OneToOne
	@JoinColumn(name = "tokenPayment", referencedColumnName = "token")
	private PaymentToken tokenPayment;
	private Double amount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private Date date;

}
