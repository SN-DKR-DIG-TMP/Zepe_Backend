package com.zepebackend.service;

import static com.zepebackend.utils.ZepeConstants.DELETE;
import static com.zepebackend.utils.ZepeConstants.PARTNER_NOT_FOUND;
import static com.zepebackend.utils.ZepeConstants.PAYMENT_TOKEN_NOT_FOUND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zepebackend.dao.partner.PartnerDaoRead;
import com.zepebackend.dao.partner.PartnerDaoWrite;
import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.dto.PartnerDto;
import com.zepebackend.entity.Partner;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;

@Service
public class PartnerService {
	final PartnerDaoRead partnerDaoRead;
	final PartnerDaoWrite partnerRepWr;
	final ModelMapper modelMapper = new ModelMapper();

	public PartnerService(PartnerDaoRead partnerRepRed, PartnerDaoWrite partnerRepWr) {
		this.partnerDaoRead = partnerRepRed;
		this.partnerRepWr = partnerRepWr;
	}

	public List<PartnerDto> getAllPartner() {
		return partnerDaoRead.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public ResponseEntity<PartnerDto> getPartnerById(Long idPartner) throws ResourceNotFoundExceptions {
		PartnerDto partner = convertEntityToDto(
				partnerDaoRead.findById(idPartner).orElseThrow(() -> new ResourceNotFoundException(PARTNER_NOT_FOUND)));
		return ResponseEntity.ok().body(partner);

	}

	public ResponseEntity<PartnerDto> findByName(String name) throws ResourceNotFoundExceptions {
		PartnerDto partner = convertEntityToDto(
				partnerDaoRead.findByName(name).orElseThrow(() -> new ResourceNotFoundExceptions(PARTNER_NOT_FOUND)));
		return ResponseEntity.ok().body(partner);
	}

	public Boolean existById(Long idPartner) throws ResourceNotFoundExceptions {
		return partnerDaoRead.existsById(idPartner);
	}

	public PartenariatDto getPartnerByIds(String[] idPartners) {
		List<Long> longList = new ArrayList<>();
		for (String s : idPartners)
			longList.add(Long.valueOf(s));
		return (PartenariatDto) partnerDaoRead.findAllById(longList);
	}

	public long partnersNumber() {
		return partnerDaoRead.count();
	}

	public PartnerDto addPartner(PartnerDto partnerdto) {
		return convertEntityToDto(partnerRepWr.save(convertDtoToEntity(partnerdto)));
	}

	public ResponseEntity<PartnerDto> updatePartner(Long idPartner, PartnerDto partnerDetails)
			throws ResourceNotFoundExceptions {
		Partner partner = partnerDaoRead.findById(idPartner)
				.orElseThrow(() -> new ResourceNotFoundExceptions(PAYMENT_TOKEN_NOT_FOUND + idPartner));
		partner.setName(partnerDetails.getName());
		partner.setAdress(partnerDetails.getAdress());
		return ResponseEntity.ok(convertEntityToDto(partnerRepWr.save(partner)));
	}

	public Map<String, Boolean> deletePartner(Long idPartner) throws ResourceNotFoundExceptions {
		Partner partner = partnerDaoRead.findById(idPartner)
				.orElseThrow(() -> new ResourceNotFoundExceptions(PARTNER_NOT_FOUND + idPartner));

		partnerRepWr.delete(partner);
		Map<String, Boolean> response = new HashMap<>();
		response.put(DELETE, Boolean.TRUE);
		return response;

	}

	public Partner getOne(Long idPartner) {
		return partnerDaoRead.getOne(idPartner);
	}

	public PartnerDto convertEntityToDto(Partner partner) {
		return modelMapper.map(partner, PartnerDto.class);
	}

	public Partner convertDtoToEntity(PartnerDto partner) {
		return modelMapper.map(partner, Partner.class);
	}

}
