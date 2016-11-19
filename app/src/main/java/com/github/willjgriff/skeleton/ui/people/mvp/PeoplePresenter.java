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
		mPeopleObservable =  mPeopleRepository.getPeopleObservable(20);
	}

	@Override
	protected void viewReady() {
		getView().showStorageLoading();
		getView().showNetworkLoading();

		addSubscription(getPeopleList().subscribe(persons -> {
			getView().hideDataLoading();
			getView().setPeople(persons);
		}, throwable -> {
			getView().handleError(throwable);
		}));

		addSubscription(getNetworkLoaded().subscribe(aBoolean -> {
			// TODO: We do this as the detail View may no longer be in sync with the list in a two pane Window.
			// Can this be abstracted? (I guess with a list-detail Fragment abstraction and base subscriptions)
			getView().closeDetailFrament();
			getView().hideNetworkLoading();
		}, throwable -> {
			getView().hideNetworkLoading();
		}));
	}

	private Observable<List<Person>> getPeopleList() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.map(ResponseHolder::getData)
			.filter(persons -> persons.size() > 0);
	}

	private Observable<Boolean> getNetworkLoaded() {
		return mPeopleObservable
			.filter(ResponseHolder::hasData)
			.filter(listResponseHolder -> listResponseHolder.getSource() == NETWORK)
			.map(listResponseHolder -> false);
	}

	@Override
	public void close() {
		mPeopleRepository.closeDataManager();
	}

	public void triggerRefreshFetch() {
		mPeopleRepository.triggerNetworkUpdate();
	}
}
