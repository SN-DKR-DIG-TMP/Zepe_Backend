package com.zepebackend.dao.facture;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Facture;

import java.util.Date;
import java.util.List;

public interface FactureDaoRead extends CoreAbstractQueryRepository<Facture, Long> {

	List<Facture> findByPartenariatEntrepriseIdPartnerAndDateFactureBetween(Long idEntreprise, Date start, Date fin);

	List<Facture> findByPartenariatCommerceIdPartnerAndDateFactureBetween(Long idCommerce, Date start, Date fin);
}
