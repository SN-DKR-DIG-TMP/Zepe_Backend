package com.zepebackend.dao.forgotpwd;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.ForgotPwd;

@Repository
public interface ForgotPwdDaoRead extends CoreAbstractQueryRepository<ForgotPwd, Long> {
	@Query(value = "select * from forgot_pwd f where f.id_user = :id_user", nativeQuery = true) 
	List<ForgotPwd> findByIdUser(@Param("id_user") String id_user);
}
