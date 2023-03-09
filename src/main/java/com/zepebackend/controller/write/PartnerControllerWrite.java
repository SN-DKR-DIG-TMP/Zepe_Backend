package com.zepebackend.controller.write;

import java.text.ParseException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.PartnerDto;
import com.zepebackend.service.PartnerService;

@RestController
@RequestMapping("/api/partner")
public class PartnerControllerWrite {
	final PartnerService partnerService;

	public PartnerControllerWrite(PartnerService partnerService) {
		this.partnerService = partnerService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	@CrossOrigin("*")
	public PartnerDto createPartner(@Validated @RequestBody PartnerDto partner) throws ParseException {
		return partnerService.addPartner(partner);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	@CrossOrigin("*")
	public ResponseEntity<PartnerDto> updatePartner(@PathVariable(value = "id") Long idPartner,
			@Validated @RequestBody PartnerDto partnerDetails) {
		return partnerService.updatePartner(idPartner, partnerDetails);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("delete/{id}")
	@CrossOrigin("*")
	public Map<String, Boolean> deletePartner(@PathVariable(value = "id") Long idPartner) {
		return partnerService.deletePartner(idPartner);
	}
}
