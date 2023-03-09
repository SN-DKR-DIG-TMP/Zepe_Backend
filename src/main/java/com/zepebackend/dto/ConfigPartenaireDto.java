package com.zepebackend.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigPartenaireDto implements Serializable {
	private List<ConfigKeyValue> configs;
	private JetonWithOutPartnerDto jetonEntreprise;
	private List<NamedConfigDto> keys;
	private String message;
}
