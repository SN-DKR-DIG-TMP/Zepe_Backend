package com.zepebackend.security.config.jwt;

import com.zepebackend.bridge.microauth.dto.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

	@Value("${api.micro.auth.url}")
	private String API_MICRO_AUTH_URL;
	RestTemplate restTemplate = new RestTemplate();

	public boolean validateJwtToken(String token) {
		return restTemplate.getForObject(API_MICRO_AUTH_URL + "jwt/validate/" + token, Boolean.class);
	}

	public String getUserNameFromJwtToken(String token) {
		return restTemplate.getForObject(API_MICRO_AUTH_URL + "jwt/userid/" + token, String.class);
	}

	@Override
	public UserDetails loadUserByUsername(String userId) {
		Account account = restTemplate.getForObject(API_MICRO_AUTH_URL + "account/" + userId, Account.class);

		if (account == null) {
			return null;
		}

		List<GrantedAuthority> authorities = account.getRoles().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new AccountDetails(account.getUsername(), account.getEmail(), authorities);
	}
}

class AccountDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;

	public AccountDetails(String username, String email, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.email = email;
		this.authorities = authorities;
	}

	/**
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
