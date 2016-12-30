package com.github.willjgriff.skeleton.di.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.willjgriff.skeleton.data.network.typeadapters.AutoValueAdapterFactory;
import com.github.willjgriff.skeleton.data.storage.sharedprefs.SharedPreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

	@Provides
	@Singleton
	SharedPreferences provideSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Provides
	@Singleton
	Gson providesGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapterFactory(AutoValueAdapterFactory.create());
		return gsonBuilder.create();
	}

	@Provides
	@Singleton
	SharedPreferencesManager providesSharedPreferenceManager(SharedPreferences sharedPreferences, Gson gson) {
		return new SharedPreferencesManager(sharedPreferences, gson);
	}
}
