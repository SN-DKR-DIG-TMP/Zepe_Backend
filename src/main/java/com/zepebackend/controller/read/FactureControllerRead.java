// BY MOMATH NDIAYE
package com.zepebackend.controller.read;

import static com.zepebackend.utils.ZepeConstants.SHORT_DATE;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.dto.FactureDto;
import com.zepebackend.service.FactureService;

@RestController
@RequestMapping("/api/facture")
public class FactureControllerRead {
	private final FactureService factureService;

	public FactureControllerRead(FactureService factureService) {
		this.factureService = factureService;
	}

	@PreAuthorize("hasRole('MODERATOR')")
	@RequestMapping(value = "/listefactureparcommerce", method = RequestMethod.GET)
	@CrossOrigin("*")
	public List<FactureDto> listeFactureParCommerceParDate(@RequestParam(name = "idPartner") Long idPartner,
			@RequestParam(name = "start") @DateTimeFormat(pattern = SHORT_DATE) Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = SHORT_DATE) Date end) {
		return factureService.listeFactureParCommerce(idPartner, start, end);
	}

	@PreAuthorize("hasRole('MODERATOR')")
	@RequestMapping(value = "/listefactureparentreprise", method = RequestMethod.GET)
	@CrossOrigin("*")
	public List<FactureDto> listeFactureParEntreprise(@RequestParam(name = "idPartner") Long idPartner,
			@RequestParam(name = "start") @DateTimeFormat(pattern = SHORT_DATE) Date start,
			@RequestParam(name = "end") @DateTimeFormat(pattern = SHORT_DATE) Date end) {
		return factureService.listeFactureParEntreprise(idPartner, start, end);
	}
}
