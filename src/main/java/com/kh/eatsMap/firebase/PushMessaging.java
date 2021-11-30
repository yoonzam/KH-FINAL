package com.kh.eatsMap.firebase;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kh.eatsMap.member.model.dto.Member;

public class PushMessaging {
	
	private static PushMessaging instance;
	
	private PushMessaging() {
		init();
	}
	
	public static PushMessaging getInstance() {
		if(instance == null) instance = new PushMessaging();
		return instance;
	}
	
	public void init() {
		
		try {
			FirebaseOptions options;
			options = FirebaseOptions.builder()
				    .setCredentials(GoogleCredentials.getApplicationDefault())
				    .build();
			FirebaseApp.initializeApp(options);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void push(Member member) {
		
		String registrationToken = "";
		try {
			
			registrationToken = member.getToken() != null ? member.getToken() : "eQ_0iBQf_oRp8XouhhSGkh:APA91bFPq7jo5noPStd3WiFk0iLl1lhxQr2jzkX1YRlv1MhPCarRI6YMhtKEE74Cr5_RiJQmjzVqxMAyDivQoGRiX45Lk8s8";
			// See documentation on defining a message payload.
			Message message = Message.builder()
			    .putData("score", "850")
			    .putData("time", "2:45")
			    .setToken(registrationToken)
			    .build();

			// Send a message to the device corresponding to the provided registration token.
			String response;
			response = FirebaseMessaging.getInstance().send(message);
			
			// Response is a message ID string.
			System.out.println("Successfully sent message: " + response);			
			
		} catch (FirebaseMessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
