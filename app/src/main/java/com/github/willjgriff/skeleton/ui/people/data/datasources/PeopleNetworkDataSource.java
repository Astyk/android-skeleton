package com.github.willjgriff.skeleton.ui.people.data.datasources;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.ListNetworkFetch;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.utils.response.NetworkResponseTransformer;
import com.github.willjgriff.skeleton.data.utils.response.RealmResponseTransformer;
import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.BasicAsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.ReplaceListRealmUpdateMethod;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by Will on 04/10/2016.
 */
public class PeopleNetworkDataSource {

	private PeopleService mPeopleService;

	public PeopleNetworkDataSource(@NonNull PeopleService peopleService) {
		mPeopleService = peopleService;
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork(int countPeople) {
		ListNetworkFetch<Person> peopleListNetworkFetch =
			new ListNetworkFetch<>(mPeopleService.getPeople(countPeople));

		return peopleListNetworkFetch.getNetworkObservable().compose(new NetworkResponseTransformer<>());
	}
}
