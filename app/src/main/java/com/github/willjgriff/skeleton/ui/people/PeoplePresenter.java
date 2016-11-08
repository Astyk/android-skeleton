package com.github.willjgriff.skeleton.ui.people;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.people.data.PeopleRepository;
import com.github.willjgriff.skeleton.ui.people.di.PeopleScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.github.willjgriff.skeleton.data.utils.response.ResponseHolder.Source.NETWORK;
import static com.github.willjgriff.skeleton.data.utils.response.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 19/08/2016.
 */
@PeopleScope
public class PeoplePresenter implements BasePresenter {

	private PeopleRepository mPeopleRepository;
	private Observable<ResponseHolder<List<Person>>> mPeopleObservable;

	@Inject
	PeoplePresenter(PeopleRepository peopleRepository) {
		mPeopleRepository = peopleRepository;
		mPeopleObservable = mPeopleRepository.getPeopleObservable(20);
	}

	public Observable<List<Person>> getPeopleList() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.map(ResponseHolder::getData);
	}

	public Observable<Throwable> getStorageErrors() {
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

	public Observable<Boolean> getStorageLoaded() {
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
		mPeopleRepository.closeDataManager();
	}

	public void triggerRefreshFetch() {
		mPeopleRepository.triggerNetworkUpdate();
	}
}
