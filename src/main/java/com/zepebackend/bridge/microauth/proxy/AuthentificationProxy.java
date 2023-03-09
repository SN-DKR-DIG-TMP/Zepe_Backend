package com.zepebackend.bridge.microauth.proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zepebackend.bridge.microauth.dto.*;
import com.zepebackend.bridge.microuser.dto.UserStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zepebackend.bridge.microuser.dto.UserAccount;
import com.zepebackend.utils.ZepeUtils;

@Service
public class AuthentificationProxy {
	private RestTemplate restTemplate = new RestTemplate();
	@Value("${api.micro.auth.url}")
	private String clientAuth;

	public ResponseEntity<MessageResponse> signup(UserAccount request, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<SignupRequest> requestEntity = new HttpEntity<SignupRequest>(
				convertUserAccountToSignupRequest(request), httpHeaders);
		return restTemplate.exchange(clientAuth + "account/signup", HttpMethod.POST, requestEntity,
				MessageResponse.class);
	}

	public ResponseEntity<MessageResponse> registerAll(List<UserAccount> accounts, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		HttpEntity<List<SignupRequest>> requestEntity = new HttpEntity<List<SignupRequest>>(
				convertUserAccountsToSignupRequests(accounts), httpHeaders);
		return restTemplate.exchange(clientAuth + "account/registerall", HttpMethod.POST, requestEntity,
				MessageResponse.class);
	}

	public ResponseEntity<AuthentificationResponse> authenticateUser(Account account) {
		ResponseEntity<AuthentificationResponse> response = restTemplate.postForEntity(clientAuth + "auth/signin",
				account, AuthentificationResponse.class);
		return response;

	}

	public ResponseEntity<?> deleteAccount(String uid, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientAuth + "account/" + uid, HttpMethod.DELETE,
				new HttpEntity<String>(httpHeaders), String.class);

	}
	
	public ResponseEntity<?> blockAccount(String uid) {
		HttpHeaders httpHeaders = new HttpHeaders();
		//httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientAuth + "auth/" + uid, HttpMethod.POST,
				new HttpEntity<String>(httpHeaders), String.class);

	}

	public ResponseEntity<String> updateRole(String uid, String newRole, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);
		UpdateRoleRequest request = new UpdateRoleRequest(uid, Arrays.asList(newRole));

		return restTemplate.exchange(clientAuth + "account/updaterole", HttpMethod.PUT,
				new HttpEntity<UpdateRoleRequest>(request, httpHeaders), String.class);
	}

	public ResponseEntity<?> updatePassword(UpdatePwdRequest upPwd, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientAuth + "account/updatepwd", HttpMethod.PUT,
				new HttpEntity<UpdatePwdRequest>(upPwd, httpHeaders), String.class);
	}
	
	//Forgot Pwd
	public ResponseEntity<?> resetPassword(UpdatePwdRequest upPwd, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientAuth + "account/resetpwd", HttpMethod.PUT,
				new HttpEntity<UpdatePwdRequest>(upPwd, httpHeaders), String.class);
	}

	public ResponseEntity<?> reactivateUser(ResetPwdRequest resetPwd, String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

		return restTemplate.exchange(clientAuth + "account/reactivate-user", HttpMethod.PUT,
				new HttpEntity<ResetPwdRequest>(resetPwd, httpHeaders), String.class);
	}

	private SignupRequest convertUserAccountToSignupRequest(UserAccount account) {
		return new SignupRequest(account.getUserId(), account.getEmail(),
				Arrays.asList(ZepeUtils.convertRole(account.getRole())), account.getPassword());
	}

	private List<SignupRequest> convertUserAccountsToSignupRequests(List<UserAccount> accounts) {
		List<SignupRequest> requests = new ArrayList<>();
		if (CollectionUtils.isEmpty(accounts)) {
			return requests;
		}

		accounts.forEach(account -> requests.add(new SignupRequest(account.getUserId(), account.getEmail(),
				Arrays.asList(ZepeUtils.convertRole(account.getRole())), account.getPassword())));
		return requests;
	}

}
