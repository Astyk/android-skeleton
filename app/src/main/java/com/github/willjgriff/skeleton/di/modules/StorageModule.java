package com.github.willjgriff.skeleton.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class StorageModule {

	@Provides
	@Singleton
	Realm providesRealm() {
		return Realm.getDefaultInstance();
	}
}
