package com.github.willjgriff.skeleton.ui.navigation;

import android.support.annotation.StringRes;

/**
 * Created by Will on 20/08/2016.
 */

public interface NavigationToolbarListener {

	void showNetworkLoadingView();

	void hideNetworkLoadingView();

	void setToolbarTitle(@StringRes int toolbarTitleRes);

}
