package com.zepebackend.dao.cashment;

import com.zepebackend.dao.write.CoreAbstractCMDRepository;
import com.zepebackend.entity.Cashment;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CashmentDaoWrite extends CoreAbstractCMDRepository<Cashment, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE cashment SET is_valid = false where token_payment = :token", nativeQuery = true)
	void updateValue(@Param("token") String token);

}
