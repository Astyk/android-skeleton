package com.github.willjgriff.skeleton.ui.people.di;

import com.github.willjgriff.skeleton.data.dataloaders.PeopleDataLoader;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.ui.people.data.PeopleDataManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 26/09/2016.
 */
@Module
public class PeopleModule {

	// TODO: This feels like a bit of a hack. It allows me to create a new Realm
	// instance for each module but use the same Realm within a module. The alternative
	// is not injecting the Realm instance at all and creating it whenever needed.
	@Provides
	@PeopleScope
	@Named("people_realm")
	Realm providesRealm(Realm realm) {
		return realm;
	}

	@Provides
	@PeopleScope
	PeopleDataLoader providesPeopleDataLoader(@Named("people_realm") Realm realm, PeopleService peopleService) {
		return new PeopleDataLoader(realm, peopleService);
	}

	@Provides
	@PeopleScope
	PeopleDataManager providesPeopleDataManager(@Named("people_realm") Realm realm, PeopleDataLoader peopleDataLoader) {
		return new PeopleDataManager(realm, peopleDataLoader);
	}
}
