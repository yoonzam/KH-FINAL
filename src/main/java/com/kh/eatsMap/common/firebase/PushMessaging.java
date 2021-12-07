package com.kh.eatsMap.common.firebase;

import java.io.IOException;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
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
		WebpushFcmOptions fcmOption = WebpushFcmOptions.builder().setLink("http://localhost:9090/main/").build();
		WebpushConfig webPushConfig = WebpushConfig.builder().setFcmOptions(fcmOption).build();
		
		try {
			
			registrationToken = member.getToken() != null ? member.getToken() : "cFLV898VLgRXt4RbmJdeLj:APA91bE-3SKyeTajHiIS4mmuSyspnnF8lgEqSG1xpp8a-gt6XqPfET5rTp9BkpTUna7mvgxRgz7ay5ohDfEda0xKpfup0mFEv_-LRS3G-YwAlY47pugU1oaCUh6I6ZR_NOt58sE-qiNN";
			// See documentation on defining a message payload.
			Message message = Message.builder()
			    .setToken(registrationToken)
			    .setWebpushConfig(webPushConfig)
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
