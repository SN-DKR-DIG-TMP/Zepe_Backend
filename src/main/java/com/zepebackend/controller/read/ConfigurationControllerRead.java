// BY MOMATH NDIAYE
package com.zepebackend.controller.read;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.ConfigPartenaireDto;
import com.zepebackend.dto.ConfigurationDto;
import com.zepebackend.service.ConfigurationService;

@RestController
@RequestMapping("/api/configuration")
@CrossOrigin("*")
public class ConfigurationControllerRead {
	private final ConfigurationService configurationService;

	public ConfigurationControllerRead(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GetMapping("/configperpartner/{idpartner}")
	public List<ConfigurationDto> getConfigPerPartner(@PathVariable("idpartner") Long idPartner) {
		return configurationService.configPerPartner(idPartner);
	}

	@GetMapping("/getConfigParPartenaire/{idpartner}")
	public ConfigPartenaireDto getConfigParPartenaire(@PathVariable("idpartner") Long idPartner) {
		return configurationService.getConfigParPartenaire(idPartner);
	}

}
