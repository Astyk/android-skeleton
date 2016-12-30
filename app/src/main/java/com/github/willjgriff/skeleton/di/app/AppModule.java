package com.github.willjgriff.skeleton.di.app;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class AppModule {

	private Context mApplication;

	AppModule(Context application) {
		mApplication = application;
	}

	@Provides
	@Singleton
	Context providesApplication() {
		return mApplication;
	}
}
