package com.zepebackend.bridge.microuser.proxy;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.zepebackend.bridge.microauth.dto.MessageResponse;
import com.zepebackend.bridge.microuser.dto.User;
import com.zepebackend.utils.ZepeConstants;

@Service
public class UserProxy<T> {
	@Value("${api.micro.users.url}")
	private String clientUser;
	RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<User> getUserByUid(String uid, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(uid, httpHeaders);
		ResponseEntity<User> responseUser = restTemplate.exchange(clientUser + "users/" + uid, HttpMethod.GET,
				requestEntity, User.class);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.USER_DOES_NOT_EXISTS);
		return responseUser;
	}

	public List<T> getUserByUids(List<String> uids, String token, Class clazz) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<List<String>> requestEntity = new HttpEntity<List<String>>(uids, httpHeaders);
		ResponseEntity<T[]> responseUser = restTemplate.exchange(clientUser + "users/uids", HttpMethod.POST,
				requestEntity, clazz);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.USER_DOES_NOT_EXISTS);
		return Arrays.asList(responseUser.getBody());
	}

	public ResponseEntity<String> deleteUser(String uid, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientUser + "users/" + uid, HttpMethod.DELETE,
				new HttpEntity<String>(httpHeaders), String.class);
	}
	
	public ResponseEntity<String> blockeUser(String uid) {
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientUser + "users/block/" + uid, HttpMethod.POST,
				new HttpEntity<String>(httpHeaders), String.class);
	}

	public ResponseEntity<User> updateUser(User userDto, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<User> requestEntity = new HttpEntity<User>(userDto, httpHeaders);
		ResponseEntity<User> responseUser = restTemplate.exchange(clientUser + "users/" + userDto.getUserId(),
				HttpMethod.PUT, requestEntity, User.class);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.UPDATE_FAILED);
		return responseUser;
	}

	public ResponseEntity<User> reactivateUser(User userDto, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<User> requestEntity = new HttpEntity<User>(userDto, httpHeaders);
		ResponseEntity<User> responseUser = restTemplate.exchange(clientUser + "users/reactivate/" + userDto.getUserId(),
				HttpMethod.PUT, requestEntity, User.class);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.UPDATE_FAILED);
		return responseUser;
	}

	public ResponseEntity<MessageResponse> addUser(User user, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, httpHeaders);
		return restTemplate.postForEntity(clientUser + "users/add", requestEntity, MessageResponse.class);
	}

	public ResponseEntity<MessageResponse> addAll(List<User> convertAccountsToUsers, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<List<User>> requestEntity = new HttpEntity<List<User>>(convertAccountsToUsers, httpHeaders);
		return restTemplate.postForEntity(clientUser + "users/addall", requestEntity, MessageResponse.class);
	}
	
	public ResponseEntity<User> getUserByMail(String email, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(email, httpHeaders);
		ResponseEntity<User> responseUser = restTemplate.exchange(clientUser + "users/search/" + email, HttpMethod.GET,
				requestEntity, User.class);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.USER_DOES_NOT_EXISTS);
		return responseUser;
	}
	
	public ResponseEntity<User> searchUser(String uid) {
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(uid, httpHeaders);
		ResponseEntity<User> responseUser = restTemplate.exchange(clientUser + "users/search/" + uid, HttpMethod.GET,
				requestEntity, User.class);
		Assert.isTrue(HttpStatus.OK.equals(responseUser.getStatusCode()), ZepeConstants.USER_DOES_NOT_EXISTS);
		return responseUser;
	}
}
