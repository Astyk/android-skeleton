package com.github.willjgriff.skeleton.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	private NavigationToolbarListener mNavigationToolbarListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mNavigationToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onResume() {
		super.onResume();
		mNavigationToolbarListener.setToolbarTitle(R.string.fragment_settings_title);
	}
}
