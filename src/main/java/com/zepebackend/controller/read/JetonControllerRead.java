package com.zepebackend.controller.read;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.JetonWithOutPartnerDto;
import com.zepebackend.service.JetonService;

@RestController
@RequestMapping("api/jeton")
@CrossOrigin("*")
public class JetonControllerRead {

	private final JetonService jetonService;

	public JetonControllerRead(JetonService jetonService) {
		this.jetonService = jetonService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/jetonparentreprise/{identreprise}")
	public JetonWithOutPartnerDto getConfigPerPartner(@PathVariable("identreprise") Long idEntreprise) {
		return jetonService.getJetonPartenaire(idEntreprise);
	}

}
