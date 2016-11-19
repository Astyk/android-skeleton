package com.github.willjgriff.skeleton.ui.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Will on 24/09/2016.
 */

public class UiUtils {

	// TODO: This should be sourced somewhere more common and kept in sync with the two pane layout width
	private static final int TWO_PANE_SCREEN_WIDTH_DP = 600;

	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return dp * (metrics.densityDpi / 160f);
	}

	public static void hideSoftKeyboard(@NonNull View view, @NonNull Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager)
			context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static boolean isTwoPaneMode(Context context) {
		int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
		float density = context.getResources().getDisplayMetrics().density;
		return (widthPixels / density) >= TWO_PANE_SCREEN_WIDTH_DP;
	}
}
