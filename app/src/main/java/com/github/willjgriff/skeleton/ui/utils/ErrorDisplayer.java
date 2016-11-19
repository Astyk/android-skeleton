package com.github.willjgriff.skeleton.ui.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.network.utils.ConnectivityUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by williamgriffiths on 22/09/2016.
 */
// TODO: The error messages could be customised dependant on the screen / api request made
public class ErrorDisplayer {

	public static void displayNetworkError(View view, Throwable throwable) {
		String errorText = getErrorMessage(view.getContext(), throwable);

		Snackbar.make(view, errorText, Snackbar.LENGTH_LONG).show();
	}

	private static String getErrorMessage(Context context, Throwable throwable) {
		if (!ConnectivityUtils.isConnected(context)) {
			return context.getString(R.string.network_error_no_internet_connection);

		} else if (throwable instanceof IOException) {
			// network error
			return context.getString(R.string.network_error_io_exception);

		} else if (throwable instanceof HttpException) {
			// non-200 response code in http request
			return context.getString(R.string.network_error_non_200_response_code);
		}
		return context.getString(R.string.network_error_unknown);
	}

	public static void displayStorageError(View view) {
		Snackbar.make(view, R.string.fragment_people_storage_error_string, Snackbar.LENGTH_LONG).show();
	}
}
