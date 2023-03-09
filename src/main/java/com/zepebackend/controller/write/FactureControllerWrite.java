// BY MOMATH NDIAYE
package com.zepebackend.controller.write;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.FactureDto;
import com.zepebackend.service.FactureService;
import com.zepebackend.utils.EtatFacture;

@RestController
@RequestMapping("/api/facture")
@CrossOrigin("*")
public class FactureControllerWrite {

	private final FactureService factureService;

	public FactureControllerWrite(FactureService factureService) {
		this.factureService = factureService;
	}

	@PreAuthorize("hasRole('MODERATOR')")
	@PutMapping("/update/{idFacture}")
	public ResponseEntity<FactureDto> updateFacture(@PathVariable(value = "idFacture") Long idFacture,
			@Validated @RequestBody EtatFacture etat) {
		return factureService.updateFacture(idFacture, etat);
	}
}
