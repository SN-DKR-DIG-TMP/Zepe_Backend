package com.zepebackend.dao.payment;

import com.zepebackend.dao.write.CoreAbstractCMDRepository;
import com.zepebackend.entity.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDaoWrite extends CoreAbstractCMDRepository<Payment, Long> {

}
