// BY MOMATH NDIAYE
package com.zepebackend.controller.write;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.ConfigurationDto;
import com.zepebackend.service.ConfigurationService;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationControllerWrite {

	private final ConfigurationService configurationService;

	public ConfigurationControllerWrite(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@PostMapping("/save")
	@CrossOrigin("*")
	public ConfigurationDto createConfiguration(@Validated @RequestBody ConfigurationDto configurationDto)
			throws ParseException {
		return configurationService.addConfiguration(configurationDto);
	}

	@PutMapping("/update/{cle}")
	@CrossOrigin("*")
	public ResponseEntity<ConfigurationDto> updateConfiguration(@PathVariable(value = "cle") String cle,
			@Validated @RequestBody ConfigurationDto configurationDto) {
		return configurationService.updateConfiguration(cle, configurationDto);
	}
}
