package com.github.willjgriff.skeleton.ui.land;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.NetworkFetchAndUpdateList;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.responsewrapper.NetworkResponseWrapper;
import com.github.willjgriff.skeleton.data.responsewrapper.RealmResponseWrapper;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
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
// specific data loaders that a single data manager looks after.
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

	public Observable<ResponseHolder<List<Person>>> getPeopleObservable(int countPeople) {
		// TODO: Make this a merge, requires proper timestamping.
		return Observable.concat(getPeopleFromCache(), getPeopleFromNetwork(countPeople));
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromCache() {
		RealmResponseWrapper<Person> realmResponseWrapper = new RealmResponseWrapper<>();
		return realmResponseWrapper.wrap(mPeopleRealmFetcher.getAsyncObservable());
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork(int countPeople) {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmSyncUpdater = new RealmSyncUpdater<>(mRealm, realmUpdateMethod);
		mPeopleNetworkFetchAndUpdateList = new NetworkFetchAndUpdateList<>(
			mPeopleService.getPeople(countPeople), realmSyncUpdater);

		NetworkResponseWrapper<Person> networkResponseWrapper = new NetworkResponseWrapper<>();
		return networkResponseWrapper.wrap(mPeopleNetworkFetchAndUpdateList.getNetworkObservable());
	}

	public void cancelUpdate() {
		// TODO: Do I need to cancel realm async transaction before closing the Realm.
		// Or can I leave it to finish?
		mPeopleNetworkFetchAndUpdateList.cancelUpdate();
		mRealm.close();
	}

}
