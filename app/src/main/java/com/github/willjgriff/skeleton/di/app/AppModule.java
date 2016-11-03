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

	Context mApplication;

	public AppModule(Context application) {
		mApplication = application;
	}

	// TODO: I don't actually need this yet. Remove this once I do, otherwise remove.
	// Note Dagger knows this and marks this modules construction as deprecated.
	@Provides
	@Singleton
	public Context providesApplication() {
		return mApplication;
	}
}
