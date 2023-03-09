package com.zepebackend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zepebackend.utils.PartnerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partner")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPartner;
	private String name;
	private String adress;
	@Enumerated(EnumType.STRING)
	private PartnerType partnerType;

}