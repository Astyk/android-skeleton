package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.People;
import com.github.willjgriff.skeleton.data.network.services.RandomPeopleService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.ReplaceRealmUpdater;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Will on 06/09/2016.
 */

public class PeopleDataManager {

	private RandomPeopleService mRandomPeopleService;
	private NetworkFetchAndUpdate<People> mPeopleNetworkFetchAndUpdate;
	private RealmFetcher<People> mRandomersRealmFetcher;

	public PeopleDataManager(@NonNull RandomPeopleService peopleService) {
		mRandomPeopleService = peopleService;
		mRandomersRealmFetcher = new AllRealmFetcher<>(People.class);
	}

	public Observable<People> getPeopleObservable(Realm realm) {
		return realm.where(People.class)
			.findAllAsync()
			.asObservable()
			.filter(new Func1<RealmResults<People>, Boolean>() {
				@Override
				public Boolean call(RealmResults<People> people) {
					return people.isLoaded();
				}
			})
			.filter(new Func1<RealmResults<People>, Boolean>() {
				@Override
				public Boolean call(RealmResults<People> people) {
					return people.isValid();
				}
			})
			.flatMap(new Func1<RealmResults<People>, Observable<People>>() {
				@Override
				public Observable<People> call(RealmResults<People> peoples) {
					return peoples.get(0).asObservable();
				}
			})
			.first()
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<People> getUpdatePeopleObservable(Realm realm) {
		RealmUpdater<People> realmUpdater = new ReplaceRealmUpdater<>(realm, mRandomersRealmFetcher);
		mPeopleNetworkFetchAndUpdate = new NetworkFetchAndUpdate<>(
			mRandomPeopleService.getPeople("40"), realmUpdater);

		return mPeopleNetworkFetchAndUpdate.fetchAndUpdateData();
	}

	public void cancelUpdate() {
		// TODO: Add check for init
		mPeopleNetworkFetchAndUpdate.cancelRequests();
	}

}
