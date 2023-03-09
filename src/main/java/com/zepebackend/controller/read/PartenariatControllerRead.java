// BY MOMATH NDIAYE
package com.zepebackend.controller.read;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.service.PartenariatService;

@RestController
@RequestMapping("/api/partenariat")
@CrossOrigin("*")
public class PartenariatControllerRead {

	private final PartenariatService partenariatService;

	public PartenariatControllerRead(PartenariatService partenariatService) {
		this.partenariatService = partenariatService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAll")
	public List<PartenariatDto> partenariatList() {
		return partenariatService.getAllPartenariat();
	}
	
	@GetMapping(value = "/getOne/{id_entreprise}")
	public List<PartenariatDto> partenariatByEntreprise(@PathVariable(value = "id_entreprise") Long id_entreprise) {
		return partenariatService.getPartenariatParEntreprise(id_entreprise);
	}
}
