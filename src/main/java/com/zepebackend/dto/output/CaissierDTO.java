package com.zepebackend.dto.output;

import com.zepebackend.dto.CashmentDto;
import com.zepebackend.dto.ConnexionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CaissierDTO extends ConnexionResponseDto implements Serializable {
	protected List<CashmentDto> encaissementDTOList;

	public CaissierDTO(ConnexionResponseDto copy) {
		super(copy);
	}
}
