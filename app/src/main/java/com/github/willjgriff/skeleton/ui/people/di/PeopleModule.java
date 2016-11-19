package com.github.willjgriff.skeleton.ui.people.di;

import com.github.willjgriff.skeleton.ui.people.data.datasources.PeopleNetworkDataSource;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.ui.people.data.PeopleRepository;
import com.github.willjgriff.skeleton.ui.people.data.datasources.PeopleStorageDataSource;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 26/09/2016.
 */
@Module
public class PeopleModule {

	// Adding a Named annotation to the provided Realm allows me to create a new Realm
	// instance for each module but use the same Realm within a module. The alternative
	// is not injecting the Realm instance at all and creating it whenever needed.
	@Provides
	@FragmentScope
	@Named("people_realm")
	Realm providesRealm(Realm realm) {
		return realm;
	}

	@Provides
	@FragmentScope
	PeopleStorageDataSource providesPeopleStorageDataSource(@Named("people_realm") Realm realm) {
		return new PeopleStorageDataSource(realm);
	}

	@Provides
	@FragmentScope
	PeopleNetworkDataSource providesPeopleNetworkDataSource(PeopleService peopleService) {
		return new PeopleNetworkDataSource(peopleService);
	}

	@Provides
	@FragmentScope
	PeopleRepository providesPeopleRepository(PeopleStorageDataSource peopleStorageDataSource,
	                                          PeopleNetworkDataSource peopleNetworkDataSource) {
		return new PeopleRepository(peopleStorageDataSource, peopleNetworkDataSource);
	}
}
