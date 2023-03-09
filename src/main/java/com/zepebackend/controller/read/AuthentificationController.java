package com.zepebackend.controller.read;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.bridge.microauth.dto.Account;
import com.zepebackend.bridge.microauth.dto.AuthentificationResponse;
import com.zepebackend.bridge.microauth.proxy.AuthentificationProxy;
import com.zepebackend.dto.ConnexionResponseDto;
import com.zepebackend.service.AuthentificationService;
import com.zepebackend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
	@Autowired
	AuthentificationService authService;
	@Autowired
	AuthentificationProxy authProxy;
	@Autowired
	UserService userService;

	@PostMapping(value = "/signin")
	@CrossOrigin("*")
	public ResponseEntity<?> auth(@RequestBody Account account) {
		if (StringUtils.isEmpty(account.getUsername()) || StringUtils.isEmpty(account.getPassword())) {
			return new ResponseEntity<>("Username ou Mot de passe incorrect!", HttpStatus.BAD_REQUEST);
		}

		ConnexionResponseDto response = new ConnexionResponseDto();
		ResponseEntity<AuthentificationResponse> principal = authProxy.authenticateUser(account);
		if (!principal.hasBody()) {
			return new ResponseEntity<>("Username ou Mot de passe incorrect!", HttpStatus.BAD_REQUEST);
		}
		response.setAccount(principal.getBody());
		return ResponseEntity.ok(authService.getUserInfo(response));
	}
	
	//Forgot Pwd
		@PostMapping("/saverequest/{uid}")
		@CrossOrigin("*")
		public ResponseEntity<?> saveForgotPwdReq(@PathVariable(value = "uid") String uid, HttpServletRequest request) {
			return userService.saveForgotPwdReq(uid, request);
		}
		
		@GetMapping(value = "search/{uid}")
		@CrossOrigin("*")
		public ResponseEntity<?> searchUser(@PathVariable(value = "uid") String uid, HttpServletRequest request) {
			return userService.searchUser(uid, request);
		}

}
