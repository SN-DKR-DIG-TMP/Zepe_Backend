package com.zepebackend.dao.forgotpwd;

import com.zepebackend.bridge.microuser.dto.ForgotPwdBadRequestDto;
import com.zepebackend.dao.write.CoreAbstractCMDRepository;
import com.zepebackend.entity.ForgotPwd;
import com.zepebackend.entity.ForgotPwdBadRequest;
import com.zepebackend.entity.UserPartner;

public interface ForgotPwdDaoWrite extends CoreAbstractCMDRepository<ForgotPwd, String>{

	ForgotPwdBadRequest save(ForgotPwdBadRequest convertDtoToEntity);

}
