package com.github.willjgriff.skeleton.ui.people.di;

import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.ui.people.data.PeopleDataManager;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 26/09/2016.
 */
@Module
public class PeopleModule {

	@Provides
	@PeopleScope
	PeopleDataManager providesPeopleDataManager(Realm realm, PeopleService peopleService) {
		return new PeopleDataManager(realm, peopleService);
	}
}
