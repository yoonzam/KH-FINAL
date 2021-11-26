package com.kh.eatsMap.common.fcm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

public class MainActivity {
	
	public MainActivity() {
		// TODO Auto-generated constructor stub
	}
	
	public static void init() {
		
		try {
			FileInputStream serviceAccount;
			serviceAccount = new FileInputStream("C:/FINAL/eatsMap/src/main/webapp/service-account-file.json");
			
			GoogleCredentials credential = GoogleCredentials.fromStream(serviceAccount);
			
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(credential)
					  .setDatabaseUrl("https://final-332911-default-rtdb.asia-southeast1.firebasedatabase.app")
					  .build();
			
			System.out.println(credential.getAccessToken());

			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void postMessage() {

		try {
			// This registration token comes from the client FCM SDKs.
			String registrationToken = "YOUR_REGISTRATION_TOKEN";

			// See documentation on defining a message payload.
			Message message = Message.builder()
			    .putData("score", "850")
			    .putData("time", "2:45")
			    .setToken(registrationToken)
			    .build();
			
			// Send a message to the device corresponding to the provided
			// registration token.
			String response;
			response = FirebaseMessaging.getInstance().send(message);
			
			// Response is a message ID string.
			System.out.println("Successfully sent message: " + response);
			
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
