package com.github.willjgriff.skeleton.di.app;

import android.content.Context;

/**
 * Created by Will on 19/08/2016.
 */

public enum AppInjector {

	INSTANCE;

	AppComponent mAppComponent;

	public void init(Context context) {
		mAppComponent = DaggerAppComponent.builder()
			.appModule(new AppModule(context))
			.networkModule(new NetworkModule())
			.storageModule(new StorageModule())
			.build();
	}

	public AppComponent getComponent() {
		return mAppComponent;
	}
}
