package com.github.willjgriff.skeleton.data.dataloaders;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.NetworkFetchAndUpdateList;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.responsewrapper.NetworkResponseTransformer;
import com.github.willjgriff.skeleton.data.responsewrapper.RealmResponseTransformer;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
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
public class PeopleDataLoader {

	private Realm mRealm;
	private PeopleService mPeopleService;
	private RealmFetcher<Person> mPeopleRealmFetcher;

	public PeopleDataLoader(@NonNull Realm realm, @NonNull PeopleService peopleService) {
		mRealm = realm;
		mPeopleService = peopleService;
		mPeopleRealmFetcher = new AllRealmFetcher<>(Person.class);
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleFromCache() {
		return mPeopleRealmFetcher.getAsyncObservable(mRealm).compose(new RealmResponseTransformer<>());
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork(int countPeople) {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmAsyncUpdater = new BasicAsyncRealmUpdater<>(mRealm, realmUpdateMethod);
		NetworkFetchAndUpdateList<Person> peopleNetworkFetchAndUpdateList = new NetworkFetchAndUpdateList<>(
			mPeopleService.getPeople(countPeople), realmAsyncUpdater);

		return peopleNetworkFetchAndUpdateList.getNetworkObservable().compose(new NetworkResponseTransformer<>());
	}
}
