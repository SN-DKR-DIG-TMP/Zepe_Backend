package com.zepebackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPartner {

	@EmbeddedId
	UserPartnerId userPartner = new UserPartnerId();
	String role;

}
