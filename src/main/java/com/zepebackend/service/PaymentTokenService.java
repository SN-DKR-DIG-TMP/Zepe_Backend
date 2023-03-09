package com.zepebackend.service;

import com.zepebackend.dao.paymenttoken.PaymentTokenDaoRead;
import com.zepebackend.dao.paymenttoken.PaymentTokenDaoWrite;
import com.zepebackend.dto.PaymentTokenDto;
import com.zepebackend.entity.PaymentToken;
import com.zepebackend.entity.PaymentToken.Status;
import com.zepebackend.utils.ZepeUtils;
import com.zepebackend.security.exceptions.ResourceNotFoundExceptions;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.zepebackend.utils.ZepeConstants.PAYMENT_TOKEN_NOT_FOUND;

@Service
public class PaymentTokenService {
    final PaymentTokenDaoRead paymentTokenRead;
    final PaymentTokenDaoWrite paymentTokenWrite;
    final ModelMapper modelMapper;

    public PaymentTokenService(PaymentTokenDaoRead paymentTokenRead, PaymentTokenDaoWrite paymentTokenWrite, ModelMapper modelMapper) {
        this.paymentTokenRead = paymentTokenRead;
        this.paymentTokenWrite = paymentTokenWrite;
        this.modelMapper = modelMapper;
    }

    public PaymentToken generatePaymentToken(String matricule) {
        PaymentToken paymentToken = new PaymentToken();
        paymentToken.setToken(ZepeUtils.generateToken());
        paymentToken.setUserMatricule(matricule);
        return paymentTokenWrite.save(paymentToken);
    }

    public ResponseEntity<PaymentToken> deactivatePaymentToken(String idPaymentToken)
            throws ResourceNotFoundExceptions {
        PaymentToken paymentToken = paymentTokenRead.findById(idPaymentToken).orElseThrow(
                () -> new ResourceNotFoundException(PAYMENT_TOKEN_NOT_FOUND + idPaymentToken));
        paymentToken.setStatus(Status.USED);
        final PaymentToken deactivatePaymentToken = paymentTokenWrite.save(paymentToken);
        return ResponseEntity.ok(deactivatePaymentToken);
    }

    public Optional<PaymentToken> findPaymentToken(String token) {
        return paymentTokenRead.findById(token);
    }

    public PaymentTokenDto genPaymentTokenDto(String matricule) {
        return modelMapper.map(generatePaymentToken(matricule), PaymentTokenDto.class);
    }

}
