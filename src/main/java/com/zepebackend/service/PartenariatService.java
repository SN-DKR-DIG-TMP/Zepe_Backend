// BY MOMATH NDIAYE
package com.zepebackend.service;

import static com.zepebackend.utils.ZepeConstants.PARTENARIAT_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepebackend.dao.partenariat.PartenariatDaoRead;
import com.zepebackend.dao.partenariat.PartenariatDaoWrite;
import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.entity.Partenariat;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import com.zepebackend.utils.ZepeConstants;

@Service
@Transactional
public class PartenariatService {

	private final PartenariatDaoRead partenariatDaoRead;
	private final PartenariatDaoWrite partenariatDaoWrite;
	private final ModelMapper modelMapper;

	public PartenariatService(PartenariatDaoRead partenariatDaoRead, PartenariatDaoWrite partenariatDaoWrite,
			ModelMapper modelMapper) {
		this.partenariatDaoRead = partenariatDaoRead;
		this.partenariatDaoWrite = partenariatDaoWrite;
		this.modelMapper = modelMapper;
	}

	public List<PartenariatDto> getAllPartenariat() {
		return partenariatDaoRead.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public List<PartenariatDto> getPartenariatParEntreprise(Long idPartenaire) {
		List<Partenariat> partenariats = partenariatDaoRead.findByEntrepriseIdPartner(idPartenaire);
		return partenariats.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public List<PartenariatDto> getPartenariatParCommerce(Long idPartenaire) {
		List<Partenariat> partenariats = partenariatDaoRead.findByCommerceIdPartner(idPartenaire);
		return partenariats.stream().map(this::convertEntityToDto).collect(Collectors.toList());
	}

	public ResponseEntity<?> addPartenariat(PartenariatDto partenariatDto) {
		if (checkPartenariatExists(partenariatDto)) {
			return ResponseEntity.badRequest().body(ZepeConstants.PARTENARIAT_ALREADY_EXIST);
		}

		Partenariat partenariat = new Partenariat();
		partenariat.setCommerce(partenariatDto.getCommerce());
		partenariat.setEntreprise(partenariatDto.getEntreprise());
		Partenariat response = partenariatDaoWrite.save(partenariat);
		return ResponseEntity.ok(convertEntityToDto(response));
	}

	public ResponseEntity<PartenariatDto> updatePartenariat(Long idPartenariat, PartenariatDto partenariatDto)
			throws ResourceNotFoundExceptions {
		Partenariat partenariat = partenariatDaoRead.findById(idPartenariat)
				.orElseThrow(() -> new ResourceNotFoundException(PARTENARIAT_NOT_FOUND + idPartenariat));
		partenariat.setActif(partenariatDto.isActif());
		final Partenariat updatedPartenariat = partenariatDaoWrite.save(partenariat);
		return ResponseEntity.ok(convertEntityToDto(updatedPartenariat));
	}

	private PartenariatDto convertEntityToDto(Partenariat partenariatDto) {
		return modelMapper.map(partenariatDto, PartenariatDto.class);
	}

	private boolean checkPartenariatExists(PartenariatDto partenariatDto) {
		return partenariatDaoRead.existsByEntrepriseIdPartnerAndCommerceIdPartner(
				partenariatDto.getEntreprise().getIdPartner(), partenariatDto.getCommerce().getIdPartner());
	}
}
