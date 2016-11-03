package com.github.willjgriff.skeleton.ui.people;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.people.data.PeopleDataManager;
import com.github.willjgriff.skeleton.ui.people.di.PeopleScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.NETWORK;
import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 19/08/2016.
 */
@PeopleScope
public class PeoplePresenter implements BasePresenter {

	private PeopleDataManager mPeopleDataManager;
	private Observable<ResponseHolder<List<Person>>> mPeopleObservable;

	@Inject
	PeoplePresenter(PeopleDataManager peopleDataManager) {
		mPeopleDataManager = peopleDataManager;
		mPeopleObservable = mPeopleDataManager.getPeopleObservable(20);
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
		mPeopleDataManager.closeDataManager();
	}

	public void triggerRefreshFetch() {
		mPeopleDataManager.triggerNetworkUpdate();
	}
}
