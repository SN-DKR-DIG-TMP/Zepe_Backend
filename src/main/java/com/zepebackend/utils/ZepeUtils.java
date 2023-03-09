package com.zepebackend.utils;

import static com.zepebackend.utils.ZepeConstants.ACCOUNT;
import static com.zepebackend.utils.ZepeConstants.DATE;
import static com.zepebackend.utils.ZepeConstants.ID_PARTNER;
import static com.zepebackend.utils.ZepeConstants.MEDIUM_DATE;
import static com.zepebackend.utils.ZepeConstants.PARTNER_ADDRESS;
import static com.zepebackend.utils.ZepeConstants.PARTNER_NAME;
import static com.zepebackend.utils.ZepeConstants.PAYMENT_TOKEN;
import static com.zepebackend.utils.ZepeConstants.SHORT_DATE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import com.zepebackend.dto.PaymentDto;

public class ZepeUtils {

	private ZepeUtils() {
	}

	public static Map<String, String> convertPaymentToData(PaymentDto payment, String token) {
		Map<String, String> data = new HashMap<>();
		if (payment == null) {
			return data;
		}
		DateFormat dateFormat = new SimpleDateFormat(MEDIUM_DATE);
		String date = dateFormat.format(payment.getDate());
		data.put(ACCOUNT, payment.getAccount());
		data.put(DATE, date);
		data.put(ID_PARTNER, payment.getTrade().getIdPartner().toString());
		data.put(PARTNER_NAME, payment.getTrade().getName());
		data.put(PARTNER_ADDRESS, payment.getTrade().getAdress());
		data.put(PAYMENT_TOKEN, token);
		return data;
	}

	public static String generateToken() {
		return UUID.randomUUID().toString();
	}

	public static Map<String, LocalDateTime> debutFinDuMois() {
		DateTime end = new DateTime(DateTimeZone.UTC).minusDays(1).withTime(23, 59, 59, 999);
		DateTime start = end.withDayOfMonth(1).withTimeAtStartOfDay();

		Map<String, LocalDateTime> data = new HashMap<>();
		data.put(ZepeConstants.START, start.toLocalDateTime());
		data.put(ZepeConstants.END, end.toLocalDateTime());

		return data;
	}

	public static Map<String, DateTime> moisEnCours() {
		DateTime end = new DateTime(DateTimeZone.UTC).withTime(23, 59, 59, 999);
		DateTime start = end.withDayOfMonth(1).withTime(0, 0, 0, 0);

		Map<String, DateTime> data = new HashMap<>();
		data.put(ZepeConstants.START, start);
		data.put(ZepeConstants.END, end);

		return data;
	}

	public static Map<String, LocalDateTime> debutFinAnnee() {
		DateTime end = new DateTime(DateTimeZone.UTC).withTime(23, 59, 59, 999);
		DateTime start = end.withDayOfYear(1).withTimeAtStartOfDay();

		Map<String, LocalDateTime> data = new HashMap<>();
		data.put(ZepeConstants.START, start.toLocalDateTime());
		data.put(ZepeConstants.END, end.toLocalDateTime());

		return data;
	}

	public static String convertDateTieToString(LocalDateTime dateTime) {
		final DateFormat SFD;
		SFD = new SimpleDateFormat(SHORT_DATE);
		if (dateTime == null) {
			return "";
		}

		return SFD.format(dateTime.toDate());
	}

	public static String convertRole(String role) {
		switch (role) {
		case ZepeConstants.CAISSIER:
		case ZepeConstants.CLIENT:
			return "ROLE_USER";

		case ZepeConstants.ADMIN_ENTREPRISE:
		case ZepeConstants.ADMIN_MEDIATION:
		case ZepeConstants.ADMIN_COMMERCE:
			return "admin";

		case ZepeConstants.GESTIONNAIRE_COMMERCE:
		case ZepeConstants.GESTIONNAIRE_ENTREPRISE:
			return "mod";

		default:
			return StringUtils.EMPTY;
		}
	}

}
