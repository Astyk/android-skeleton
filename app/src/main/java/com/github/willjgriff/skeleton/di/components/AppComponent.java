package com.github.willjgriff.skeleton.di.components;

import com.github.willjgriff.skeleton.di.modules.AppModule;
import com.github.willjgriff.skeleton.di.modules.NetworkModule;
import com.github.willjgriff.skeleton.di.modules.StorageModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by Will on 18/08/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, StorageModule.class})
public interface AppComponent {

	Realm providesRealm();

	Retrofit providesRetrofit();

}
