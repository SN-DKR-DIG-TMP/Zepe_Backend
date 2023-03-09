package com.zepebackend.dao.partenariat;

import java.util.List;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Partenariat;

public interface PartenariatDaoRead extends CoreAbstractQueryRepository<Partenariat, Long> {
	List<Partenariat> findByEntrepriseIdPartner(Long idPartner);

	List<Partenariat> findByCommerceIdPartner(Long idPartner);

	boolean existsByEntrepriseIdPartnerAndCommerceIdPartner(Long idEntreprise, Long idCommerce);
}
