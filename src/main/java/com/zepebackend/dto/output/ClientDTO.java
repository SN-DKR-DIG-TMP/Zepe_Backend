package com.zepebackend.dto.output;

import java.io.Serializable;
import java.util.List;

import com.zepebackend.dto.ConfigPartenaireDto;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.dto.PaymentDto;
import com.zepebackend.dto.PaymentTokenDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO extends ConnexionResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<PaymentDto> paymentDTOList;
	private PaymentTokenDto paymentTokenDto;
	private ConfigPartenaireDto configPartenaireDto;

	public ClientDTO(ConnexionResponseDto copy) {
		super(copy);
	}
}
