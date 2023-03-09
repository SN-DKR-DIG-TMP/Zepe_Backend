package com.zepebackend.controller.read;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.service.PartnerService;

@RestController
@RequestMapping("/api/partner")
@CrossOrigin("*")
public class PartnerControllerRead {
	final PartnerService partnerService;

	public PartnerControllerRead(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAll")
	public List<PartnerDto> getAllPartner() {
		return partnerService.getAllPartner();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<PartnerDto> getPartnerById(@PathVariable(value = "id") Long idPartner) {
		return partnerService.getPartnerById(idPartner);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/exist/{id}")
	public Boolean existById(@PathVariable(value = "id") Long idProfil) {
		return partnerService.existById(idProfil);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get/{ids}")
	public PartenariatDto getPartnersByIds(@PathVariable(value = "ids") String[] idPartners) {
		return partnerService.getPartnerByIds(idPartners);
	}
}
