package com.zepebackend.dao.paymenttoken;

import com.zepebackend.dao.write.CoreAbstractCMDRepository;
import com.zepebackend.entity.PaymentToken;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTokenDaoWrite extends CoreAbstractCMDRepository<PaymentToken, String>  {

}
