package com.zepebackend.utils;

public final class ZepeConstants {

	// ZEPE UTILS
	public static final String START = "debut";
	public static final String END = "fin";
	public static final String DELETE = "deleted";
	public static final String MEDIUM_DATE = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_DATE = "dd/MM/yyyy";
	public static final String SHORT_DATE_EN = "yyyy-MM-dd";
	public static final String ACCOUNT = "account";
	public static final String DATE = "date";
	public static final String ID_PARTNER = "ipPartner";
	public static final String PARTNER_NAME = "partnerName";
	public static final String PARTNER_ADDRESS = "partnerAdress";
	public static final String PAYMENT_TOKEN = "paymentToken";
	public static final String DATA_LOAD = "data-load";
	public static final String DEFAULT_PWD = "Atos2021!";

	// EXCEPTION
	public static final String CONFIGURATION_KEY_EXCEPTION = "Configuration not found for this key :: ";
	public static final String JETON_NOT_FOUND = "Jeton not found for this key : ";

	// LOGGER
	public static final String PARTENARIAT_NOT_FOUND = "Partenariat not found for this key :: ";
	public static final String PARTENARIAT_ALREADY_EXIST = "Partenariat already exists";
	public static final String CONFIG_NOT_FOUND = "can not get the config for this partner , ";
	public static final String FACTURE_NOT_FOUND = "Facture not found for this key, ";
	public static final String NOT_UPDATE = "can not update this configuration, key not found, ";
	public static final String PAYMENT_TOKEN_NOT_FOUND = "PaymentToken not found for this id :: ";
	public static final String PAYMENT_OR_CASHMENT_TOKEN_NOT_FOUND = "cashment object or the token payment are null ,";
	public static final String PARTNER_NOT_FOUND = "Partner not found";
	public static final String FIREBASE_INITIALIZED = "Firebase application has been initialized";
	public static final String FIREBASE_TOKEN_MESSAGE = "Sent message to token. Device token: ";
	public static final String UNAUTHORIZED = "Unauthorized error: {}";
	public static final String PARTNER_JETON_ALREADY_EXIST = "Partner already has a jeton.";
	public static final String USER_REGISTRATION_FAILED = "Signup failed with status: ";
	public static final String USER_DOES_NOT_EXISTS = "User not found.";
	public static final String UPDATE_FAILED = "Update failed.";

	// ZEPE ROLES
	public static final String CAISSIER = "CAISSIER";
	public static final String CLIENT = "CLIENT";
	public static final String ADMIN_ENTREPRISE = "ADMIN_ENTREPRISE";
	public static final String ADMIN_MEDIATION = "ADMIN_MEDIATION";
	public static final String GESTIONNAIRE_COMMERCE = "GESTIONNAIRE_COMMERCE";
	public static final String GESTIONNAIRE_ENTREPRISE = "GESTIONNAIRE_ENTREPRISE";
	public static final String ADMIN_COMMERCE = "ADMIN_COMMERCE";

	// Config keys
	public static final String NB_MAX_TICKET_KEY = "atos.ticket.Model.nbTicket";
	public static final String NB_MAX_TICKET_NAME = "Max ticket / personne";
	public static final String DEFAULT_PWD_KEY = "atos.mdp.defaut";
	public static final String DEFAULT_PWD_NAME = "Mot de passe par défaut";
	public static final String AMOUNT_EXCEED_AUTORISED_KEY = "atos.amount.exceed.autorised";
	public static final String AMOUNT_EXCEED_AUTORISED_NAME = "Montant de dépassement autorisé";
	public static final String MINIMUM_AMOUNT_KEY = "atos.minimum.amount";
	public static final String MINIMUM_AMOUNT_NAME = "Montant minimum";
	
	private ZepeConstants() {
	}
}
