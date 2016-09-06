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
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 06/09/2016.
 */

public class RandomPeopleDataManager {

	private RandomPeopleService mRandomPeopleService;
	private NetworkFetchAndUpdate<People> mSoRandomersNetworkFetchAndUpdate;
	private RealmFetcher<People> mRandomersRealmFetcher;
	private Subscription mRandomersUpdateSubscription;
	private PublishSubject<People> mRandomersPublishSubject;

	public RandomPeopleDataManager(@NonNull RandomPeopleService peopleService) {
		mRandomPeopleService = peopleService;
		mRandomersRealmFetcher = new AllRealmFetcher<>(People.class);
		mRandomersPublishSubject = PublishSubject.create();
	}

	public People getStoredRandomers(Realm realm) {
		RealmResults<People> savedRandomers = mRandomersRealmFetcher.fetch(realm);

		if (savedRandomers.size() >= 1) {
			return savedRandomers.get(0);
		} else {
			return null;
		}
	}

	public Observable<People> getRandomersUpdateObservable() {
		return mRandomersPublishSubject.asObservable();
	}

	public void updateRandomersFromNetwork(Realm realm) {
		if (mRandomersUpdateSubscription == null || mRandomersUpdateSubscription.isUnsubscribed()) {
			RealmUpdater<People> realmUpdater = new ReplaceRealmUpdater<>(realm, mRandomersRealmFetcher);
			mSoRandomersNetworkFetchAndUpdate = new NetworkFetchAndUpdate<>(realm,
				mRandomPeopleService.getPeople("100"), realmUpdater);

			mRandomersUpdateSubscription = mSoRandomersNetworkFetchAndUpdate.fetchAndUpdateData().subscribe(new Action1<People>() {
				@Override
				public void call(People people) {
					mRandomersPublishSubject.onNext(people);
				}
			});
		}
	}

	public void cancelUpdate() {
		if (mRandomersUpdateSubscription != null && !mRandomersUpdateSubscription.isUnsubscribed()) {
			mRandomersUpdateSubscription.unsubscribe();
		}
		mSoRandomersNetworkFetchAndUpdate.cancelRequests();
	}

}
