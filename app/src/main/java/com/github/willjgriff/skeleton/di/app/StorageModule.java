package com.github.willjgriff.skeleton.di.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class StorageModule {

	// No @Singleton annotation as the DataManager will close the Realm instance
	// once it's done with it. Requiring a new one to be created for other DataManager's.
	@Provides
	Realm providesRealm() {
		return Realm.getDefaultInstance();
	}
}
