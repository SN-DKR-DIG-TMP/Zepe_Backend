package com.zepebackend.controller.read;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.dto.ForgotPwdRequestDto;
import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.dto.UserPartnerDto;
import com.zepebackend.entity.ForgotPwd;
import com.zepebackend.entity.UserPartner;
import com.zepebackend.service.PartnerService;
import com.zepebackend.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserControllerRead {
	
	final UserService userService;
	private final RestTemplate restTemplate;
	
	public UserControllerRead(UserService userService, RestTemplateBuilder restTemplateBuilder) {
		this.userService = userService;
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@GetMapping("/forgotPwdReq")
	public List<ForgotPwd> getAll(){
		return userService.getAllForgotPwdRequest();
	}
	
	@GetMapping("/getVersion")
	public String getVersion(){
		String url = "http://193.43.134.150:8999/version.json";
		return this.restTemplate.getForObject(url, String.class);
	}
}
