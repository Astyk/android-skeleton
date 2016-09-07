package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.api.ApiInjector;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector {

	INSTANCE;

	public LandComponent getComponent() {
		return DaggerLandComponent.builder()
			.apiComponent(ApiInjector.INSTANCE.getComponent())
			.build();
	}
}
