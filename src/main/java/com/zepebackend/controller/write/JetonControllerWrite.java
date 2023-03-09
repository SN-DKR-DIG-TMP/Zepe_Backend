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

import com.zepebackend.dto.JetonDto;
import com.zepebackend.service.JetonService;

@RestController
@RequestMapping("/api/jeton")
public class JetonControllerWrite {

	private final JetonService jetonService;

	public JetonControllerWrite(JetonService jetonService) {
		this.jetonService = jetonService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save")
	@CrossOrigin("*")
	public ResponseEntity<?> createJeton(@Validated @RequestBody JetonDto jetonDto) throws ParseException {
		return jetonService.addJeton(jetonDto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{idjeton}")
	@CrossOrigin("*")
	public ResponseEntity<JetonDto> updateConfiguration(@PathVariable(value = "idjeton") Long idJeton,
			@Validated @RequestBody JetonDto jetonDto) {
		return jetonService.updateJeton(idJeton, jetonDto);
	}

}
