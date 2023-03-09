package com.zepebackend.fcm;

public class PushNotificationRequest {
	public PushNotificationRequest(String message, String paymentToken, String customerTokenFirebase) {
		super();
		this.message = message;
		this.paymentToken = paymentToken;
		this.customerTokenFirebase = customerTokenFirebase;
	}

	private String topic;
	private String message;
	private String paymentToken;
	private String customerTokenFirebase;

	public String getCustomerTokenFirebase() {
		return customerTokenFirebase;
	}

	public void setCustomerTokenFirebase(String customerTokenFirebase) {
		this.customerTokenFirebase = customerTokenFirebase;
	}

	public String getPaymentToken() {
		return paymentToken;
	}

	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
