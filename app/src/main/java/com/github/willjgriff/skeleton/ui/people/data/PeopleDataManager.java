package com.github.willjgriff.skeleton.ui.people.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.customtransformers.TakeUntilNetwork;
import com.github.willjgriff.skeleton.data.dataloaders.PeopleDataLoader;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
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

	public PeopleDataManager(@NonNull Realm realm, @NonNull PeopleDataLoader peopleDataLoader) {
		mRealm = realm;
		mPeopleDataLoader = peopleDataLoader;
		mNetworkFetchTrigger = PublishSubject.create();
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleObservable(int countPeople) {
		return Observable
			.merge(getPeopleFromNetworkTrigger(countPeople), mPeopleDataLoader.getPeopleFromCache())
			.compose(new TakeUntilNetwork<>())
			.replay(1)
			.autoConnect();
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
