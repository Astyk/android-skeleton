package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.helpers.ErrorHolder;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.ConnectableObservable;

/**
 * Created by Will on 19/08/2016.
 */
// TODO: Abstract the View - Presenter binding behaviour into a base class
@LandScope
public class LandPresenter implements BasePresenter<LandView> {

	private PeopleDataManager mPeopleDataManager;
	private WeakReference<LandView> mLandView;
	private Subscription mPeopleSubscription;

	// Errors should have a separate Observable.
	private ConnectableObservable <ErrorHolder<List<Person>>> mPeople;

	public Observable<Integer> count;

	public Observable<Void> refreshTrigger;

	@Inject
	LandPresenter(PeopleDataManager peopleDataManager) {
		mPeopleDataManager = peopleDataManager;
	}

	public LandPresenter() {

		// do this in the setter
		a = refreshTrigger.startWith(Void).flatMap( mPeopleDataManager.getPeopleObservable().replay() )


//		a = mPeopleDataManager.getPeopleObservable().replay()
		mPeople = a.map( result.list );
		mErroMessage = a.map( result.error.text ).asDriver(  )
		count = mPeople.count();
	}

	public Observable<ErrorHolder<List<Person>>> getPeople() {
		return mPeople;
	}

	@Override
	public void onDestroy() {
		// TODO: Should I be able to do this / emulate this
//		mPeople.dispose()
	}














	@Override
	public void bindView(LandView view) {



		mLandView = new WeakReference<>(view);

		if (mPeopleSubscription == null || mPeopleSubscription.isUnsubscribed()) {
			setupPeopleSubscription();
		}

		mPeopleDataManager.updatePeople();
	}

	private void setupPeopleSubscription() {
		getView().showInitialLoading();
		getView().showNetworkLoading();

		mPeopleSubscription = mPeopleDataManager.getPeopleObservable()
			// TODO: Can be replaced with Action1?
			.subscribe(new Subscriber<ErrorHolder<List<Person>>>() {
				@Override
				public void onCompleted() {

				}

				@Override
				public void onError(Throwable e) {

				}

				@Override
				public void onNext(ErrorHolder<List<Person>> listErrorHolder) {
					if (listErrorHolder.hasError()) {
						getView().showError();
//						getView().hideNetworkLoading();
//						getView().hideInitialLoading();
					} else if (listErrorHolder.getData() != null) {
						getView().setPeople(listErrorHolder.getData());
						// TODO: Add this to some sort of doOnFirst filter for hiding the initial loading.
						getView().hideInitialLoading();
						getView().hideNetworkLoading();
					}
				}
			});
	}

	private LandView getView() {
		return mLandView.get();
	}

	@Override
	public void unbindView() {
		if (mPeopleSubscription != null && !mPeopleSubscription.isUnsubscribed()) {
			mPeopleSubscription.unsubscribe();
		}
	}


}
