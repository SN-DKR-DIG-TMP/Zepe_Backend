package com.zepebackend.dto.output;

import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.dto.ConfigPartenaireDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ConnexionDTO implements Serializable {
	private User user;
	private ConfigPartenaireDto configPartenaireDto;

}
