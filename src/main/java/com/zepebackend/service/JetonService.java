// BY MOMATH NDIAYE
package com.zepebackend.service;

import static com.zepebackend.utils.ZepeConstants.JETON_NOT_FOUND;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zepebackend.dao.jeton.JetonDaoRead;
import com.zepebackend.dao.jeton.JetonDaoWrite;
import com.zepebackend.dto.JetonDto;
import com.zepebackend.dto.JetonWithOutPartnerDto;
import com.zepebackend.entity.Jeton;
import com.zepebackend.entity.Partner;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import com.zepebackend.utils.ZepeConstants;

@Service
@Transactional
public class JetonService {
	private final JetonDaoRead jetonDaoRead;
	private final JetonDaoWrite jetonDaoWrite;

	private final PartnerService partnerService;
	private final ModelMapper modelMapper;

	public JetonService(JetonDaoRead jetonDaoRead, JetonDaoWrite jetonDaoWrite, PartnerService partnerService,
			ModelMapper modelMapper) {
		this.jetonDaoRead = jetonDaoRead;
		this.jetonDaoWrite = jetonDaoWrite;
		this.partnerService = partnerService;
		this.modelMapper = modelMapper;
	}

	public ResponseEntity<?> addJeton(JetonDto jetonDto) {
		if (jetonDto == null || jetonDto.getPartner() == null) {
			return ResponseEntity.noContent().build();
		}
		if (jetonDaoRead.existsByPartnerIdPartner(jetonDto.getPartner().getIdPartner())) {
			return ResponseEntity.badRequest().body(ZepeConstants.PARTNER_JETON_ALREADY_EXIST);
		}
		return ResponseEntity.ok(convertEntityToDto(jetonDaoWrite.save(convertDtoToEntity(jetonDto))));
	}

	public ResponseEntity<JetonDto> updateJeton(Long idJeton, JetonDto jetonDto) throws ResourceNotFoundExceptions {
		Jeton jeton = jetonDaoRead.findById(idJeton)
				.orElseThrow(() -> new ResourceNotFoundExceptions(JETON_NOT_FOUND + idJeton));
		jeton.setMontantEntreprise(jetonDto.getMontantEntreprise());
		jeton.setMontantEmploye(jetonDto.getMontantEmploye());
		final Jeton updatedJeton = jetonDaoWrite.save(jeton);
		return ResponseEntity.ok(convertEntityToDto(updatedJeton));
	}

	private JetonDto convertEntityToDto(Jeton jeton) {
		return modelMapper.map(jeton, JetonDto.class);
	}

	private Jeton convertDtoToEntity(JetonDto jetonDto) {
		return modelMapper.map(jetonDto, Jeton.class);
	}

	public JetonWithOutPartnerDto getJetonPartenaire(Long idPartner) {
		Partner partner = partnerService.getOne(idPartner);
		Jeton jeton = jetonDaoRead.findByPartner(partner);
		JetonWithOutPartnerDto jetonWithOutPartnerDto = new JetonWithOutPartnerDto();
		if (jeton != null){
			jetonWithOutPartnerDto.setIdJeton(jeton.getIdJeton());
			jetonWithOutPartnerDto.setMontantEmploye(jeton.getMontantEmploye());
			jetonWithOutPartnerDto.setMontantEntreprise(jeton.getMontantEntreprise());
		}
		return jetonWithOutPartnerDto;
	}
}
