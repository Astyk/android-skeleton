package com.github.willjgriff.skeleton.ui.people2.di;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.data.query.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.converters.RealmPersonDataConverter;
import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.models.person2.RealmPerson2;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.storage.CacheRealmDiskDataSource;
import com.github.willjgriff.skeleton.ui.people.di.FragmentScope;
import com.github.willjgriff.skeleton.ui.people2.data.People2NetworkDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 23/12/2016.
 *
 * The commented code below highlights how easy it is to swap between
 * Realm and SharedPreferences for the local storage datasource.
 */
@Module
public class People2Module {

	@Provides
	@FragmentScope
	CacheRealmDiskDataSource<Person2, RealmPerson2, PeopleQuery> providesDiskDataSource() {
		return new CacheRealmDiskDataSource<>(RealmPerson2.class, new RealmPersonDataConverter());
	}

//	@Provides
//	@FragmentScope
//	People2SharedPrefsDataSource providesSharedPrefsDataSource(SharedPreferencesManager sharedPreferencesManager) {
//		return new People2SharedPrefsDataSource(sharedPreferencesManager);
//	}

	@Provides
	@FragmentScope
	People2NetworkDataSource providesNetworkDataSource(PeopleService peopleService) {
		return new People2NetworkDataSource(peopleService);
	}

	@Provides
	@FragmentScope
	ListCacheRepository<Person2, PeopleQuery> providesRepository(CacheRealmDiskDataSource<Person2, RealmPerson2, PeopleQuery> people2DiskDataSource, People2NetworkDataSource people2NetworkDataSource) {
//	ListCacheRepository<Person2, PeopleQuery> providesRepository(People2SharedPrefsDataSource people2DiskDataSource, People2NetworkDataSource people2NetworkDataSource) {
		return new ListCacheRepository<>(people2NetworkDataSource, people2DiskDataSource);
	}
}
