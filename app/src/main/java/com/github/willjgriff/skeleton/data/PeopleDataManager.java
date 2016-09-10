package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;
import com.github.willjgriff.skeleton.data.models.People;
import com.github.willjgriff.skeleton.data.network.services.RandomPeopleService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.ReplaceRealmUpdater;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 06/09/2016.
 */
// TODO: Abstract this so we can have individual data loaders that a single data manager looks after.
// Only once we're happy with the setup
public class PeopleDataManager {

	private Realm mRealm;
	private RandomPeopleService mPeopleService;
	private NetworkFetchAndUpdate<People> mPeopleNetworkFetchAndUpdate;
	private RealmFetcher<People> mPeopleRealmFetcher;
	private PublishSubject<ErrorHolder<People>> mPeoplePublishSubject;
	private Subscription mUpdateSubscription;

	public PeopleDataManager(@NonNull Realm realm, @NonNull RandomPeopleService peopleService) {
		mRealm = realm;
		mPeopleService = peopleService;
		mPeopleRealmFetcher = new AllRealmFetcher<>(People.class, realm);
		mPeoplePublishSubject = PublishSubject.create();
	}

	// We must subscribe to this before we publish data to it, even if we unsubscribe.
	// Items published before any subscriptions made will be not be emitted or cached.
	public Observable<ErrorHolder<People>> getPeopleObservable() {
		return mPeoplePublishSubject.asObservable().serialize().cache();
	}

	public void updatePeople() {
		mUpdateSubscription = Observable
			// TODO: Make this a merge.
			.concat(getPeopleFromCache(), getPeopleFromNetwork())
			.subscribe(new Action1<ErrorHolder<People>>() {
				@Override
				public void call(ErrorHolder<People> peopleErrorHolder) {
					mPeoplePublishSubject.onNext(peopleErrorHolder);
				}
			});
	}

	private Observable<ErrorHolder<People>> getPeopleFromCache() {
		return mPeopleRealmFetcher.fetchObservable();
	}

	private Observable<ErrorHolder<People>> getPeopleFromNetwork() {
		RealmUpdater<People> realmUpdater = new ReplaceRealmUpdater<>(mRealm, mPeopleRealmFetcher);
		mPeopleNetworkFetchAndUpdate = new NetworkFetchAndUpdate<>(mPeopleService.getPeople("40"), realmUpdater);

		return mPeopleNetworkFetchAndUpdate.fetchAndUpdateData();
	}

	public void cancelUpdate() {
		if (mUpdateSubscription != null && !mUpdateSubscription.isUnsubscribed()) {
			mUpdateSubscription.unsubscribe();
		}
		// TODO: Do I need to cancel realm async transaction before closing the Realm.
		// Or can I leave it to finish?
		mPeopleNetworkFetchAndUpdate.cancelUpdate();
		mRealm.close();
	}

}
