package com.zepebackend.controller.write;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zepebackend.bridge.microauth.dto.UpdatePwdRequest;
import com.zepebackend.bridge.microuser.dto.ForgotPwdRequest;
import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.bridge.microuser.dto.UserAccount;
import com.zepebackend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControllerWrite {
	@Autowired
	private UserService userService;

	
	@PostMapping("/add")
	@CrossOrigin("*")
	public ResponseEntity<?> registerUser(@RequestBody UserAccount request,
			@RequestHeader(name = "Authorization") String token) {
		return userService.registerUser(request, token);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addall")
	@CrossOrigin("*")
	public ResponseEntity<?> registerAll(@RequestBody List<UserAccount> request,
			@RequestHeader(name = "Authorization") String token) {
		return userService.registerAll(request, token);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update")
	@CrossOrigin("*")
	public ResponseEntity<?> updateUser(@RequestBody User userDto,
			@RequestHeader(name = "Authorization") String token) {
		return userService.updateUser(userDto, token);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/reactivate-user")
	@CrossOrigin("*")
	public ResponseEntity<?> reactivateUser(@RequestBody User userDto,
										@RequestHeader(name = "Authorization") String token) {
		return userService.reactivateUser(userDto, token);
	}

	@PutMapping("/updatepwd")
	@CrossOrigin("*")
	public ResponseEntity<?> updatePassword(@RequestBody UpdatePwdRequest upPwd,
			@RequestHeader(name = "Authorization") String token) {
		return userService.updatePassword(upPwd, token);

	}
	
	@PutMapping("/resetpwd")
	@CrossOrigin("*")
	public ResponseEntity<?> resetPassword(@RequestBody UpdatePwdRequest upPwd,
			@RequestHeader(name = "Authorization") String token) {
		return userService.resetPassword(upPwd, token);

	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{uid}")
	@CrossOrigin("*")
	public ResponseEntity<String> deleteUser(@PathVariable("uid") String uid,
			@RequestHeader(name = "Authorization") String token) {
		return userService.deleteUser(uid, token);
	}
}
