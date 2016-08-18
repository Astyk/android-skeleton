package com.github.willjgriff.skeleton.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class AppModule {

	Application mApplication;

	public AppModule(Application application) {
		mApplication = application;
	}

	@Provides
	@Singleton
	public Application providesApplication() {
		return mApplication;
	}
}
