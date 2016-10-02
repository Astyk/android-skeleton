package com.github.willjgriff.skeleton;

import android.app.Application;

import com.github.willjgriff.skeleton.di.app.AppInjector;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Will on 18/08/2016.
 */

public class SkeletonApplication extends Application {

	private static SkeletonApplication sApplication;

	// Use this sparingly, ideally get the Context from Activities or Fragments.
	// If needing this, question if the functionality is in the right place.
	public static SkeletonApplication app() {
		return sApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sApplication = this;

		Realm.init(this);

		RealmConfiguration realmConfig = new RealmConfiguration.Builder()
			.deleteRealmIfMigrationNeeded()
			.build();

		Realm.setDefaultConfiguration(realmConfig);

		AppInjector.INSTANCE.init(this);
	}
}
