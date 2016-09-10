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
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 06/09/2016.
 */

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
		mPeopleRealmFetcher = new AllRealmFetcher<>(People.class);
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
		return mPeopleRealmFetcher.fetchAsync(mRealm)
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
			.first()
			.map(new Func1<RealmResults<People>, People>() {
				@Override
				public People call(RealmResults<People> people) {
					return people.get(0);
				}
			})
			.map(new Func1<People, ErrorHolder<People>>() {
				@Override
				public ErrorHolder<People> call(People people) {
					ErrorHolder<People> errorHolder = new ErrorHolder<>();
					errorHolder.setData(people);
					return errorHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ErrorHolder<People>>() {
				@Override
				public ErrorHolder<People> call(Throwable throwable) {
					ErrorHolder<People> errorHolder = new ErrorHolder<>();
					errorHolder.setError(throwable);
					return errorHolder;
				}
			});
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
