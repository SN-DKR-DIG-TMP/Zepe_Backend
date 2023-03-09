package com.zepebackend.dao.userpartner;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.UserPartner;

import java.util.List;

public interface UserPartnerDaoRead extends CoreAbstractQueryRepository<UserPartner, String> {
	UserPartner findByUserPartnerIdUser(String idUser);

	List<UserPartner> findByUserPartnerPartnerIdPartner(long idPartner);

	List<UserPartner> findAllByRoleIn(List<String> role);

}
