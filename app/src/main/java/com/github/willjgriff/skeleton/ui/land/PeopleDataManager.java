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
import com.github.willjgriff.skeleton.data.storage.updaters.AsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.BasicAsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.ReplaceListRealmUpdateMethod;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.subjects.PublishSubject;

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
	private PublishSubject<Void> mNetworkFetchTrigger;
	private boolean mNetworkDataFetched = false;

	public PeopleDataManager(@NonNull Realm realm, @NonNull RandomPeopleService peopleService) {
		mRealm = realm;
		mPeopleService = peopleService;
		mPeopleRealmFetcher = new AllRealmFetcher<>(Person.class);
		mNetworkFetchTrigger = PublishSubject.create();
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleObservable(int countPeople) {
		// This HAS to be a merge, not a concat, to enable the network trigger to work.
		return Observable.merge(getPeopleFromCache(), getPeopleFromNetworkTrigger(countPeople))
			.doOnNext(listResponseHolder -> {
				if (ResponseHolder.Source.NETWORK == listResponseHolder.getSource() && !listResponseHolder.hasError()) {
					// TODO: there must be a better way of preventing data coming in the wrong order.
					mNetworkDataFetched = true;
				}
			})
			.filter(listResponseHolder
				-> ResponseHolder.Source.STORAGE != listResponseHolder.getSource() || !mNetworkDataFetched);
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromCache() {
		RealmResponseWrapper<Person> realmResponseWrapper = new RealmResponseWrapper<>();
		return realmResponseWrapper.wrap(mPeopleRealmFetcher.getAsyncObservable(mRealm));
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromNetworkTrigger(int countPeople) {
		return mNetworkFetchTrigger.flatMap(aVoid -> getPeopleFromNetwork(countPeople));
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork(int countPeople) {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmAsyncUpdater = new BasicAsyncRealmUpdater<>(mRealm, realmUpdateMethod);
		mPeopleNetworkFetchAndUpdateList = new NetworkFetchAndUpdateList<>(
			mPeopleService.getPeople(countPeople), realmAsyncUpdater);

		NetworkResponseWrapper<Person> networkResponseWrapper = new NetworkResponseWrapper<>();
		return networkResponseWrapper.wrap(mPeopleNetworkFetchAndUpdateList.getNetworkObservable());
	}

	public void triggerNetworkUpdate() {
		mNetworkFetchTrigger.onNext(null);
	}

	public void closeDataManager() {
		mRealm.close();
	}

}
