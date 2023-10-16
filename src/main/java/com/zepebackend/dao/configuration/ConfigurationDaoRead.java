// BY MOMATH NDIAYE
package com.zepebackend.dao.configuration;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Configuration;
import com.zepebackend.entity.ConfigurationId;
import com.zepebackend.entity.Partner;

@Repository
public interface ConfigurationDaoRead extends CoreAbstractQueryRepository<Configuration, ConfigurationId> {

	// Liste config par partenaire
	// @Query("select c from Configuration c where c.partner.idPartner = :x")
	// List<Configuration> listConfigPerPartner(@Param("x") Long idPartner);
	List<Configuration> findByConfigurationIdPartner(Partner partner);
		Optional<Configuration> findByConfigurationIdPartnerIdPartnerAndConfigurationIdCle(Long partnerId, String key);
}
