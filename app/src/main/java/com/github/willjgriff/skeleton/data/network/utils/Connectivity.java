package com.github.willjgriff.skeleton.data.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by williamgriffiths on 22/09/2016.
 */
public class Connectivity {

	/**
	 * Check if there is any connectivity.
	 *
	 * @param context Current application context
	 * @return <b>True</b> if device network is connected
	 */
	public static boolean isConnected(final Context context) {
		if(context != null) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		}else{
			return false;
		}
	}
}
