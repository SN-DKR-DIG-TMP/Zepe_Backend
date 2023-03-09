package com.zepebackend.dao.paymenttoken;

import com.zepebackend.dao.read.CoreAbstractQueryRepository;
import com.zepebackend.entity.PaymentToken;
import org.springframework.stereotype.Repository;
@Repository
public interface PaymentTokenDaoRead extends  CoreAbstractQueryRepository<PaymentToken, String> {

}
