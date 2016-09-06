package com.github.willjgriff.skeleton.di.app;

import com.github.willjgriff.skeleton.data.network.services.QuestionsService;
import com.github.willjgriff.skeleton.data.network.services.RandomPeopleService;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by Will on 18/08/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, StorageModule.class})
public interface AppComponent {

	Realm providesRealm();

	QuestionsService providesQuestionsService();

	RandomPeopleService providesRandomPeopleService();
}
