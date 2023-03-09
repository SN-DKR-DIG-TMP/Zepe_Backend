package com.zepebackend.fcm;

import com.zepebackend.dto.PaymentDto;
import com.zepebackend.utils.ZepeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushNotificationService {
	private final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
	private final FCMService fcmService;

	public PushNotificationService(FCMService fcmService) {
		this.fcmService = fcmService;
	}

	public void sendPushNotification(PaymentDto payment, String tokenPayment, PushNotificationRequest request) {
		Map<String,String> data= ZepeUtils.convertPaymentToData(payment,tokenPayment);

		try {
			fcmService.sendMessageToToken(data, request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
