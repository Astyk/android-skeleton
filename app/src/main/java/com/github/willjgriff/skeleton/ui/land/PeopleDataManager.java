package com.github.willjgriff.skeleton.ui.land;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.NetworkFetchAndUpdateList;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder;
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

	public PeopleDataManager(@NonNull Realm realm, @NonNull RandomPeopleService peopleService) {
		mRealm = realm;
		mPeopleService = peopleService;
		mPeopleRealmFetcher = new AllRealmFetcher<>(Person.class, realm);
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleObservable() {
//		return mPeoplePublishSubject.asObservable().serialize().replay(1).autoConnect();
		// TODO: Make this a merge and add timestamps.
		return Observable.concat(getPeopleFromCache(), getPeopleFromNetwork());
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromCache() {
		return mPeopleRealmFetcher.fetchAsyncObservable();
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork() {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmSyncUpdater = new RealmSyncUpdater<>(mRealm, realmUpdateMethod);
		mPeopleNetworkFetchAndUpdateList = new NetworkFetchAndUpdateList<>(mPeopleService.getPeople("3"), realmSyncUpdater);

		return mPeopleNetworkFetchAndUpdateList.getNetworkObservable();
	}

	public void cancelUpdate() {
		// TODO: Do I need to cancel realm async transaction before closing the Realm.
		// Or can I leave it to finish?
		mPeopleNetworkFetchAndUpdateList.cancelUpdate();
		mRealm.close();
	}

}
