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

import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.service.PartenariatService;

@RestController
@RequestMapping("/api/partenariat")
public class PartenariatControllerWrite {

	private final PartenariatService partenariatService;

	public PartenariatControllerWrite(PartenariatService partenariatService) {
		this.partenariatService = partenariatService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	@CrossOrigin("*")
	public ResponseEntity<?> createPartenariat(@Validated @RequestBody PartenariatDto partenariatDto)
			throws ParseException {
		return partenariatService.addPartenariat(partenariatDto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{idpartenariat}")
	@CrossOrigin("*")
	public ResponseEntity<PartenariatDto> updatePartenariat(@PathVariable(value = "idpartenariat") Long idPartenariat,
			@Validated @RequestBody PartenariatDto partenariatDto) {
		return partenariatService.updatePartenariat(idPartenariat, partenariatDto);
	}

}
