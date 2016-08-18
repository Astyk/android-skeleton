package com.github.willjgriff.skeleton;

import android.app.Application;

/**
 * Created by Will on 18/08/2016.
 */

public class SkeletonApplication extends Application {

	private static SkeletonApplication sApplication;

	// Use this sparingly, ideally get the Context from Activities or Fragments.
	// If using this, question if the functionality is in the right place.
	public static SkeletonApplication app() {
		return sApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sApplication = this;


	}
}
