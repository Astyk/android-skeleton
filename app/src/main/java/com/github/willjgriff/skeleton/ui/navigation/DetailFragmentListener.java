package com.github.willjgriff.skeleton.ui.navigation;

import android.support.v4.app.Fragment;

/**
 * Created by Will on 24/09/2016.
 */
public interface DetailFragmentListener {

	void openDetailFragment(Fragment fragment);

	void closeDetailFragment();

	boolean twoPaneViewEnabled();
}
