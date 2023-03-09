package com.zepebackend.dao.configuration;

import com.zepebackend.dao.write.CoreAbstractCMDRepository;
import com.zepebackend.entity.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationDaoWrite extends CoreAbstractCMDRepository<Configuration, Long> {
}
