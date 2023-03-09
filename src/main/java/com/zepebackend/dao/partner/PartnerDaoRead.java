package com.zepebackend.dao.partner;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.Partner;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerDaoRead extends  CoreAbstractQueryRepository<Partner, Long>  {
	Optional<Partner>findByName(String name);

}
