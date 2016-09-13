package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.observables.ConnectableObservable;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.NETWORK;
import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter {

	private PeopleDataManager mPeopleDataManager;
	private ConnectableObservable<ResponseHolder<List<Person>>> mPeopleObservable;

	@Inject
	LandPresenter(PeopleDataManager peopleDataManager) {
		mPeopleDataManager = peopleDataManager;
		setPeopleObservable();
	}

	private void setPeopleObservable() {
		// replay(1) will emit the last value emitted for each new subscription.
//		if (mPeopleObservable == null) {
		mPeopleObservable = mPeopleDataManager.getPeopleObservable(20).replay(1);
		// TODO: Find out how this actually behaves.
		mPeopleObservable.connect();
//		}
	}

	public Observable<List<Person>> getPeopleList() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.map(ResponseHolder::getData);
	}

	public Observable<Throwable> getCacheErrors() {
		return mPeopleObservable
			.filter(ResponseHolder::hasError)
			.filter(listResponseHolder -> listResponseHolder.getSource() == STORAGE)
			.map(ResponseHolder::getError);
	}

	public Observable<Throwable> getNetworkErrors() {
		return mPeopleObservable
			.filter(ResponseHolder::hasError)
			.filter(listResponseHolder -> listResponseHolder.getSource() == NETWORK)
			.map(ResponseHolder::getError);
	}

	public Observable<Boolean> getCacheLoaded() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.filter(listResponseHolder -> listResponseHolder.getData().size() > 0)
			.filter(listResponseHolder -> listResponseHolder.getSource() == STORAGE)
			.map(listResponseHolder -> false);
	}

	public Observable<Boolean> getNetworkLoaded() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.filter(listResponseHolder -> listResponseHolder.getSource() == NETWORK)
			.map(listResponseHolder -> false);
	}

	@Override
	public void cancelUpdate() {
		mPeopleDataManager.cancelUpdate();
	}
}
