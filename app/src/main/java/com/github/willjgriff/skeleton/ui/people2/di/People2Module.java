package com.github.willjgriff.skeleton.ui.people2.di;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.data.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.converters.PersonStorageConverter;
import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.models.person2.StoragePerson2;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.storage.BasicDiskDataSource;
import com.github.willjgriff.skeleton.data.storage.sharedprefs.SharedPreferencesManager;
import com.github.willjgriff.skeleton.ui.people.di.FragmentScope;
import com.github.willjgriff.skeleton.ui.people2.data.People2NetworkDataSource;
import com.github.willjgriff.skeleton.ui.people2.data.People2SharedPrefsDataSource;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Will on 23/12/2016.
 *
 * Below highlights how easy it is to swap between Realm and SharedPreferences for the local storage datasource.
 */
@Module
public class People2Module {

	@Provides
	@FragmentScope
	@Named("people2_realm")
	Realm providesRealm(Realm realm) {
		return realm;
	}

//	@Provides
//	@FragmentScope
//	BasicDiskDataSource<Person2, StoragePerson2, PeopleQuery> providesDiskDataSource(@Named("people2_realm") Realm realm) {
//		return new BasicDiskDataSource<>(realm, StoragePerson2.class, new PersonStorageConverter());
//	}

	@Provides
	@FragmentScope
	People2SharedPrefsDataSource providesSharedPrefsDataSource(SharedPreferencesManager sharedPreferencesManager) {
		return new People2SharedPrefsDataSource(sharedPreferencesManager);
	}

	@Provides
	@FragmentScope
	People2NetworkDataSource providesNetworkDataSource(PeopleService peopleService) {
		return new People2NetworkDataSource(peopleService);
	}

	@Provides
	@FragmentScope
//	ListCacheRepository<Person2, PeopleQuery> providesRepository(BasicDiskDataSource<Person2, StoragePerson2, PeopleQuery> people2DiskDataSource, People2NetworkDataSource people2NetworkDataSource) {
	ListCacheRepository<Person2, PeopleQuery> providesRepository(People2SharedPrefsDataSource people2SharedPrefsDataSource, People2NetworkDataSource people2NetworkDataSource) {
		return new ListCacheRepository<>(people2NetworkDataSource, people2SharedPrefsDataSource);
	}
}
