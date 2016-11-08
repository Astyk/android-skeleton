package com.github.willjgriff.skeleton.ui.navigation;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.willjgriff.skeleton.R;

/**
 * Created by Will on 08/11/2016.
 */

public class NavigationRouter {

	private FragmentManager mFragmentManager;

	public NavigationRouter(FragmentManager fragmentManager) {
		mFragmentManager = fragmentManager;
	}

	public boolean isDifferentFragment(Fragment navigationFragment) {
		Fragment currentFragment = mFragmentManager.findFragmentById(R.id.activity_navigation_container);
		return currentFragment == null || !currentFragment.getTag().equals(navigationFragment.getClass().toString());
	}

	public void switchFragmentInContainer(Fragment navigationFragment, @IdRes int container) {
		mFragmentManager
			.beginTransaction()
			.replace(container,
				navigationFragment,
				navigationFragment.getClass().toString())
			.commit();
	}

	public void removeDetailFragment() {
		if (isDetailPaneLoaded()) {
			Fragment detailsFragment = mFragmentManager.findFragmentById(R.id.activity_navigation_details_container);
			mFragmentManager.beginTransaction().remove(detailsFragment).commit();
		}
	}

	private boolean isDetailPaneLoaded() {
		return mFragmentManager.findFragmentById(R.id.activity_navigation_details_container) != null;
	}
}
