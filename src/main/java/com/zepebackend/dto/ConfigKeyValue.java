package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigKeyValue {
	private NamedConfigDto key;
	private int intValue;
	private String strValue;
}
