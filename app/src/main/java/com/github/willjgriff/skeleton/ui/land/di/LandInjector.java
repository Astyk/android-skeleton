package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.api.ApiInjector;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector {

	INSTANCE;

	LandComponent mLandComponent;

	// Retaining this component is necessary if the fragment that
	// uses it can be recreated before it's navigated away from.
	public LandComponent getComponent() {
		if (mLandComponent == null) {
			mLandComponent = DaggerLandComponent.builder()
				.apiComponent(ApiInjector.INSTANCE.getComponent())
				.build();
		}
		return mLandComponent;
	}

	public void invalidate() {
		mLandComponent = null;
	}
}
