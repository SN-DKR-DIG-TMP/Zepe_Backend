package com.zepebackend.dao.jeton;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Jeton;
import com.zepebackend.entity.Partner;

public interface JetonDaoRead extends CoreAbstractQueryRepository<Jeton, Long> {

	// Liste jeton par partenaire
	@Query("select  j from Jeton   j where  j.partner.idPartner=:x")
	Set<Jeton> listJetonParEntreprise(@Param("x") Long idEntreprise);

	Jeton findByPartner(Partner partner);

	boolean existsByPartnerIdPartner(Long idPartner);

}
