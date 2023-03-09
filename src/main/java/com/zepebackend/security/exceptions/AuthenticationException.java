package com.zepebackend.security.exceptions;

public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PWD_NO_VALID = "Login ou mot de passe non valide.";
	private static final String USER_ALREADY_EXISTS = "Cet utilisateur existe déjà.";
	private static final String TOKEN_NOT_VALID = "Token non valide.";

	private AuthenticationException(String message) {
		super(message);
	}

	public static AuthenticationException getLoginPasswordNotValid() {
		return new AuthenticationException(LOGIN_PWD_NO_VALID);
	}

	public static AuthenticationException getUserAlreadyExists() {
		return new AuthenticationException(USER_ALREADY_EXISTS);
	}

	public static AuthenticationException getTokenNotValid() {
		return new AuthenticationException(TOKEN_NOT_VALID);
	}
}
