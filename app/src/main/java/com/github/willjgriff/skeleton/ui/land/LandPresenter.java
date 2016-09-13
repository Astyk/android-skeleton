package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

import static com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder.Source.NETWORK;
import static com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 19/08/2016.
 */
// TODO: Abstract the View - Presenter binding behaviour into a base class
@LandScope
public class LandPresenter implements BasePresenter {

	// TODO: Make functions to return each of these.
	public Observable<List<Person>> mListPeople;
	public Observable<Throwable> mErrorObservable;
	public Observable<Boolean> mPeopleLoadedFromCache;
	public Observable<Boolean> mPeopleLoadedFromNetwork;
	private PeopleDataManager mPeopleDataManager;
	private ConnectableObservable<ResponseHolder<List<Person>>> mPeopleObservable;

	@Inject
	LandPresenter(PeopleDataManager peopleDataManager) {
		mPeopleDataManager = peopleDataManager;

		// replay(1) will emit the last value emitted for each new subscription.
//		if (mPeopleObservable == null) {
		mPeopleObservable = mPeopleDataManager.getPeopleObservable().replay(1);
		mPeopleObservable.connect();
//		}


		Observable<ResponseHolder<List<Person>>> validPeopleObservable = mPeopleObservable.filter(new Func1<ResponseHolder<List<Person>>, Boolean>() {
			@Override
			public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.hasData();
			}
		});

		mListPeople = validPeopleObservable.map(new Func1<ResponseHolder<List<Person>>, List<Person>>() {
			@Override
			public List<Person> call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.getData();
			}
		});

		// TODO: Not sure about these.
		mPeopleLoadedFromCache = validPeopleObservable.filter(new Func1<ResponseHolder<List<Person>>, Boolean>() {
			@Override
			public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.getData().size() > 0 && listResponseHolder.getSource() == STORAGE;
			}
		})
			.distinct()
			.map(new Func1<ResponseHolder<List<Person>>, Boolean>() {
				@Override
				public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
					return true;
				}
			});

		mPeopleLoadedFromNetwork = validPeopleObservable.filter(new Func1<ResponseHolder<List<Person>>, Boolean>() {
			@Override
			public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.getSource() == NETWORK;
			}
		}).map(new Func1<ResponseHolder<List<Person>>, Boolean>() {
			@Override
			public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
				return true;
			}
		});

		mErrorObservable = mPeopleObservable.filter(new Func1<ResponseHolder<List<Person>>, Boolean>() {
			@Override
			public Boolean call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.hasError();
			}
		}).map(new Func1<ResponseHolder<List<Person>>, Throwable>() {
			@Override
			public Throwable call(ResponseHolder<List<Person>> listResponseHolder) {
				return listResponseHolder.getError();
			}
		});
	}

	@Override
	public void cancelLoading() {
		mPeopleDataManager.cancelUpdate();
	}
}
