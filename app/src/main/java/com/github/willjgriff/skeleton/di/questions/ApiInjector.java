package com.github.willjgriff.skeleton.di.questions;

import com.github.willjgriff.skeleton.di.app.AppInjector;

/**
 * Created by Will on 19/08/2016.
 */

public enum ApiInjector {

	INSTANCE;

	// We create a new instance every time as we need a new Realm Object
	public ApiComponent getComponent() {
		return DaggerApiComponent.builder()
			.appComponent(AppInjector.INSTANCE.getComponent())
			.apiModule(new ApiModule())
			.build();
	}

}
