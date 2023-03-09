package com.zepebackend.dto;

import java.io.Serializable;
import java.util.Date;

import com.zepebackend.entity.Partner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartenariatDto implements Serializable {
	private Long idPartenariat;
	private Partner entreprise;
	private Partner commerce;
	private boolean actif = true;
	private Date dateCreation = new Date();
}
