package com.zepebackend.fcm;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.zepebackend.utils.ZepeConstants.FIREBASE_TOKEN_MESSAGE;

@Service
public class FCMService {
	private final Logger logger = LoggerFactory.getLogger(FCMService.class);

	public void sendMessageToToken(Map<String, String> data, PushNotificationRequest request)
			throws InterruptedException, ExecutionException {
		Message message = getPreconfiguredMessageWithData(data, request);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(message);
		String response = sendAndGetResponse(message);
		logger.info(FIREBASE_TOKEN_MESSAGE + request.getCustomerTokenFirebase() + ", " + response
				+ " msg " + jsonOutput);
	}

	private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
		return FirebaseMessaging.getInstance().sendAsync(message).get();
	}

	private AndroidConfig getAndroidConfig(String topic) {
		return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
				.setPriority(AndroidConfig.Priority.HIGH)
				.setNotification(AndroidNotification.builder().setTag(topic).build()).build();
	}

	private ApnsConfig getApnsConfig(String topic) {
		return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
	}

	private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
		return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getCustomerTokenFirebase())
				.build();
	}

	private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
		AndroidConfig androidConfig = getAndroidConfig(request.getCustomerTokenFirebase());
		ApnsConfig apnsConfig = getApnsConfig(request.getCustomerTokenFirebase());
		return Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig)
				.setNotification(new Notification(request.getMessage(), request.getPaymentToken()));
	}
}
