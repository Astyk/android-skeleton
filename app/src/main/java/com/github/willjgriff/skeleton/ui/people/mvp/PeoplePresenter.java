package com.github.willjgriff.skeleton.ui.people.mvp;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;
import com.github.willjgriff.skeleton.mvp.BaseMvpPresenter;
import com.github.willjgriff.skeleton.ui.people.data.PeopleRepository;
import com.github.willjgriff.skeleton.ui.people.di.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.github.willjgriff.skeleton.data.utils.response.ResponseHolder.Source.NETWORK;
import static com.github.willjgriff.skeleton.data.utils.response.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 19/08/2016.
 */
@FragmentScope
public class PeoplePresenter extends BaseMvpPresenter<PeopleView> {

	private PeopleRepository mPeopleRepository;
	private Observable<ResponseHolder<List<Person>>> mPeopleObservable;

	@Inject
	PeoplePresenter(PeopleRepository peopleRepository) {
		mPeopleRepository = peopleRepository;
		mPeopleObservable = mPeopleRepository.getPeopleObservable(20);
	}

	@Override
	protected void viewReady() {
		getView().showStorageLoading();
		getView().showNetworkLoading();

		addSubscription(getPeopleList().subscribe(persons -> {
			getView().setPeople(persons);
		}));

		addSubscription(getStorageLoaded().subscribe(aBoolean -> {
			getView().hideStorageLoading();
		}));

		addSubscription(getNetworkLoaded().subscribe(aBoolean -> {
			// This is necessary as after the network has loaded the detail
			// View may no longer be in sync with the list in a two pane Window.
			getView().closeDetailFrament();
			getView().hideNetworkLoading();
			getView().hideStorageLoading();
		}));

		addSubscription(getStorageErrors().subscribe(throwable -> {
			getView().showStorageError();
			getView().hideStorageLoading();
		}));

		addSubscription(getNetworkErrors().subscribe(throwable -> {
			getView().showNetworkError(throwable);
			getView().hideNetworkLoading();
			getView().hideStorageLoading();
		}));
	}

	private Observable<List<Person>> getPeopleList() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.map(ResponseHolder::getData);
	}

	private Observable<Boolean> getStorageLoaded() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.filter(listResponseHolder -> listResponseHolder.getData().size() > 0)
			.filter(listResponseHolder -> listResponseHolder.getSource() == STORAGE)
			.map(listResponseHolder -> false);
	}

	private Observable<Boolean> getNetworkLoaded() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.filter(listResponseHolder -> listResponseHolder.getSource() == NETWORK)
			.map(listResponseHolder -> false);
	}

	private Observable<Throwable> getStorageErrors() {
		return mPeopleObservable
			.filter(ResponseHolder::hasError)
			.filter(listResponseHolder -> listResponseHolder.getSource() == STORAGE)
			.map(ResponseHolder::getError);
	}

	private Observable<Throwable> getNetworkErrors() {
		return mPeopleObservable
			.filter(ResponseHolder::hasError)
			.filter(listResponseHolder -> listResponseHolder.getSource() == NETWORK)
			.map(ResponseHolder::getError);
	}

	@Override
	public void close() {
		mPeopleRepository.closeDataManager();
	}

	public void triggerRefreshFetch() {
		mPeopleRepository.triggerNetworkUpdate();
	}
}
