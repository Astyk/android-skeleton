package com.github.willjgriff.skeleton.ui.land;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.NetworkFetchAndUpdateList;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.models.helpers.ErrorHolder;
import com.github.willjgriff.skeleton.data.network.services.RandomPeopleService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmSyncUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.ReplaceListRealmUpdateMethod;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 06/09/2016.
 */
// TODO: Once we're happy with the setup, abstract this so we can have
// individual data loaders that a single data manager looks after.
public class PeopleDataManager {

	private Realm mRealm;
	private RandomPeopleService mPeopleService;
	private NetworkFetchAndUpdateList<Person> mPeopleNetworkFetchAndUpdateList;
	private RealmFetcher<Person> mPeopleRealmFetcher;
	private PublishSubject<ErrorHolder<List<Person>>> mPeoplePublishSubject;
	private Subscription mUpdateSubscription;

	public PeopleDataManager(@NonNull Realm realm, @NonNull RandomPeopleService peopleService) {
		mRealm = realm;
		mPeopleService = peopleService;
		mPeopleRealmFetcher = new AllRealmFetcher<>(Person.class, realm);
		mPeoplePublishSubject = PublishSubject.create();
	}

	// We must subscribe to this before we publish data to it, even if we unsubscribe.
	// Items published before any subscriptions made will be not be emitted or cached.
	public Observable<ErrorHolder<List<Person>>> getPeopleObservable() {
		return mPeoplePublishSubject.asObservable().serialize().cache();
	}

	public void updatePeople() {
		mUpdateSubscription = Observable
			// TODO: Make this a merge with a timestamp.
			.concat(getPeopleFromCache(), getPeopleFromNetwork())
			.subscribe(new Action1<ErrorHolder<List<Person>>>() {
				@Override
				public void call(ErrorHolder<List<Person>> errorHolder) {
					mPeoplePublishSubject.onNext(errorHolder);
				}
			});
	}

	private Observable<ErrorHolder<List<Person>>> getPeopleFromCache() {
		return mPeopleRealmFetcher.fetchAsyncObservable();
	}

	private Observable<ErrorHolder<List<Person>>> getPeopleFromNetwork() {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmSyncUpdater = new RealmSyncUpdater<>(mRealm, realmUpdateMethod);
		mPeopleNetworkFetchAndUpdateList = new NetworkFetchAndUpdateList<>(mPeopleService.getPeople("3"), realmSyncUpdater);

		return mPeopleNetworkFetchAndUpdateList.getNetworkObservable();
	}

	public void cancelUpdate() {
		if (mUpdateSubscription != null && !mUpdateSubscription.isUnsubscribed()) {
			mUpdateSubscription.unsubscribe();
		}
		// TODO: Do I need to cancel realm async transaction before closing the Realm.
		// Or can I leave it to finish?
		mPeopleNetworkFetchAndUpdateList.cancelUpdate();
		mRealm.close();
	}

}
