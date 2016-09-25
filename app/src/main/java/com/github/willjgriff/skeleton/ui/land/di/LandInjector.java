package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.ComponentInvalidator;
import com.github.willjgriff.skeleton.di.api.ApiInjector;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector implements ComponentInvalidator {

	INSTANCE;

	LandComponent mLandComponent;

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
