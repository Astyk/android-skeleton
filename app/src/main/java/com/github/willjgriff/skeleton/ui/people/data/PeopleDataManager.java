package com.github.willjgriff.skeleton.ui.people.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.dataloaders.PeopleDataLoader;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 06/09/2016.
 */
// DataManagers encapsulate all a screen's data requirements
// DataLoaders encapsulate fetching of a specific data model
public class PeopleDataManager {

	private Realm mRealm;
	private PeopleDataLoader mPeopleDataLoader;
	private PublishSubject<Void> mNetworkFetchTrigger;
	private boolean mNetworkDataFetched = false;

	public PeopleDataManager(@NonNull Realm realm, @NonNull PeopleDataLoader peopleDataLoader) {
		mRealm = realm;
		mPeopleDataLoader = peopleDataLoader;
		mNetworkFetchTrigger = PublishSubject.create();
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleObservable(int countPeople) {
		// This HAS to be a merge, not a concat, to enable the network trigger to work.
		return getPeopleFromNetworkTrigger(countPeople)
			.mergeWith(mPeopleDataLoader.getPeopleFromCache())
			.doOnNext(listResponseHolder -> {
				if (ResponseHolder.Source.NETWORK == listResponseHolder.getSource() && !listResponseHolder.hasError()) {
					// TODO: there must be a better way of preventing data coming in the wrong order.
					mNetworkDataFetched = true;
				}
			})
			.filter(listResponseHolder
				-> !(ResponseHolder.Source.STORAGE == listResponseHolder.getSource() && mNetworkDataFetched));
	}

	private Observable<ResponseHolder<List<Person>>> getPeopleFromNetworkTrigger(int countPeople) {
		return mNetworkFetchTrigger
			.startWith((Void) null)
			.flatMap(aVoid -> mPeopleDataLoader.getPeopleFromNetwork(countPeople));
	}

	public void triggerNetworkUpdate() {
		mNetworkFetchTrigger.onNext(null);
	}

	public void closeDataManager() {
		mRealm.close();
	}

}
