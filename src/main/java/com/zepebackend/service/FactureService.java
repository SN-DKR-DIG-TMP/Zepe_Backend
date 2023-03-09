// BY MOMATH NDIAYE
package com.zepebackend.service;

import static com.zepebackend.utils.ZepeConstants.DELETE;
import static com.zepebackend.utils.ZepeConstants.END;
import static com.zepebackend.utils.ZepeConstants.FACTURE_NOT_FOUND;
import static com.zepebackend.utils.ZepeConstants.START;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepebackend.dao.facture.FactureDaoRead;
import com.zepebackend.dao.facture.FactureDaoWrite;
import com.zepebackend.dto.FactureDto;
import com.zepebackend.entity.Facture;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import com.zepebackend.utils.EtatFacture;
import com.zepebackend.utils.ZepeUtils;

@Service
@Transactional
public class FactureService {
	private final FactureDaoRead factureDaoRead;
	private final FactureDaoWrite factureDaoWrite;
	private final ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(FactureService.class);

	public FactureService(FactureDaoRead factureDaoRead, FactureDaoWrite factureDaoWrite, ModelMapper modelMapper) {
		this.factureDaoRead = factureDaoRead;
		this.factureDaoWrite = factureDaoWrite;
		this.modelMapper = modelMapper;
	}

	public List<FactureDto> getAllFacture() {
		return factureDaoRead.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public List<FactureDto> listeFactureParEntreprise(Long idEntreprise) {
		Map<String, LocalDateTime> dates = ZepeUtils.debutFinAnnee();
		return factureDaoRead.findByPartenariatEntrepriseIdPartnerAndDateFactureBetween(idEntreprise,
				dates.get(START).toDate(), dates.get(END).toDate()).stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
	}

	public List<FactureDto> listeFactureParCommerce(Long idCommerce, Date start, Date end) {
		return factureDaoRead.findByPartenariatCommerceIdPartnerAndDateFactureBetween(idCommerce, start, end).stream()
				.map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public List<FactureDto> listeFactureParEntreprise(Long idEntreprise, Date start, Date end) {
		return factureDaoRead.findByPartenariatEntrepriseIdPartnerAndDateFactureBetween(idEntreprise, start, end)
				.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public long factureNumber() {
		return factureDaoRead.count();
	}

	public ResponseEntity<FactureDto> updateFacture(Long idFacture, EtatFacture etat)
			throws ResourceNotFoundExceptions {
		Facture facture = factureDaoRead.findById(idFacture)
				.orElseThrow(() -> new ResourceNotFoundException(FACTURE_NOT_FOUND + idFacture));
		logger.warn(FACTURE_NOT_FOUND, idFacture);
		facture.setEtat(etat);
		return ResponseEntity.ok(convertEntityToDto(factureDaoWrite.save(facture)));
	}

	public Map<String, Boolean> deleteFacture(Long idFacture) throws ResourceNotFoundExceptions {
		Facture facture = factureDaoRead.findById(idFacture)
				.orElseThrow(() -> new ResourceNotFoundException(FACTURE_NOT_FOUND + idFacture));
		logger.warn(FACTURE_NOT_FOUND, idFacture);

		factureDaoWrite.delete(facture);
		Map<String, Boolean> response = new HashMap<>();
		response.put(DELETE, Boolean.TRUE);
		return response;
	}

	private FactureDto convertEntityToDto(Facture facture) {
		return modelMapper.map(facture, FactureDto.class);
	}

}
