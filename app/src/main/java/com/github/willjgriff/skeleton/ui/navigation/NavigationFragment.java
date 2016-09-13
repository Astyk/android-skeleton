package com.github.willjgriff.skeleton.ui.navigation;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.land.LandFragment;
import com.github.willjgriff.skeleton.ui.settings.SettingsFragment;

/**
 * Created by Will on 20/08/2016.
 */

public enum NavigationFragment {

	LAND(R.string.navigation_land, LandFragment.class),
	SETTINGS(R.string.navigation_settings, SettingsFragment.class);

	@StringRes
	private int mNavigationTitle;
	private Class<? extends Fragment> mFragmentClass;

	NavigationFragment(int fragmentTitle, Class<? extends Fragment> fragmentClass) {
		mNavigationTitle = fragmentTitle;
		mFragmentClass = fragmentClass;
	}

	public int getNavigationTitle() {
		return mNavigationTitle;
	}

	public Class<? extends Fragment> getFragmentClass() {
		return mFragmentClass;
	}
}
