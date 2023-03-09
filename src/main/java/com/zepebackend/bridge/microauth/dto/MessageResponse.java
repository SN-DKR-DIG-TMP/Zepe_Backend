package com.zepebackend.bridge.microauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode
public class MessageResponse {
	private String message;
}
