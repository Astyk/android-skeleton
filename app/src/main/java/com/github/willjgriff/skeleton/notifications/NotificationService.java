package com.github.willjgriff.skeleton.notifications;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by williamgriffiths on 12/09/2016.
 */
public class NotificationService extends FirebaseMessagingService {

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);

		Log.d("NOTIFI", "From " + remoteMessage.getFrom());

	}
}
