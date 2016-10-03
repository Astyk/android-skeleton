package com.github.willjgriff.skeleton.ui.navigation;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.form.PaginationTest;
import com.github.willjgriff.skeleton.ui.people.PeopleFragment;
import com.github.willjgriff.skeleton.ui.settings.SettingsFragment;

/**
 * Created by Will on 03/10/2016.
 */

public class NavigationFragmentFactory {

	public Fragment getFragmentFromId(@IdRes int navigationFragmentId) {
		Fragment navigationFragment;

		switch (navigationFragmentId) {
			case R.id.navigation_people_fragment:
				navigationFragment = new PeopleFragment();
				break;
			case R.id.navigation_form_fragment:
				navigationFragment = new PaginationTest();
				break;
			case R.id.navigation_settings:
				navigationFragment = new SettingsFragment();
				break;
			default:
				navigationFragment = new PeopleFragment();
		}

		return navigationFragment;
	}
}
