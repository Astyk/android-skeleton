package com.github.willjgriff.skeleton.di.api;

import com.github.willjgriff.skeleton.data.PeopleDataManager;
import com.github.willjgriff.skeleton.data.network.services.RandomPeopleService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class ApiModule {

	@Provides
	@FragmentScope
	PeopleDataManager providesRandomPeopleDataManager(RandomPeopleService peopleService) {
		return new PeopleDataManager(peopleService);
	}

}
