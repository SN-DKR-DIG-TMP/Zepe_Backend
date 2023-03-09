package com.zepebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTokenDto {
    private String userMatricule;
    private String token;
    private Status status = Status.UNUSED;

    public enum Status {
        USED, UNUSED
    }


}
