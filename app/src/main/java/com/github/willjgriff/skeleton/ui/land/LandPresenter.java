package com.github.willjgriff.skeleton.ui.land;

import android.util.Log;

import com.github.willjgriff.skeleton.data.PeopleDataManager;
import com.github.willjgriff.skeleton.data.models.People;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter<LandView> {

	private PeopleDataManager mPeopleDataManager;
	private WeakReference<LandView> mLandView;
	private Realm mRealm;
	private CompositeSubscription mCompositeSubscription;

	@Inject
	LandPresenter(PeopleDataManager peopleDataManager) {
		// TODO: Inject into DataManager or here
		mPeopleDataManager = peopleDataManager;
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public void bindView(LandView view) {
		mLandView = new WeakReference<>(view);

		if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
			mCompositeSubscription = new CompositeSubscription();
			getPeople();
			updatePeople();
		}
	}

	private void getPeople() {
		getView().showInitialLoading();
		Subscription peopleDataSubscription = mPeopleDataManager.getPeopleObservable(mRealm)
			.subscribe(new Subscriber<People>() {
				@Override
				public void onCompleted() {

				}

				@Override
				public void onError(Throwable e) {
					//TODO: Deal with when the DB is empty.
				}

				@Override
				public void onNext(People people) {
					getView().setPeople(people.getPeople());
					// TODO: Add this to some sort of doOnFirst filter for hiding the initial loading.
					getView().hideInitialLoading();
				}
			});
		mCompositeSubscription.add(peopleDataSubscription);
	}

	private void updatePeople() {
		getView().showNetworkLoading();
		Subscription peopleNetworkUpdateSubscription = mPeopleDataManager.getUpdatePeopleObservable(mRealm)
			.subscribe(new Subscriber<People>() {
				@Override
				public void onCompleted() {

				}

				@Override
				public void onError(Throwable e) {
					getView().showError();
					getView().hideNetworkLoading();
				}

				@Override
				public void onNext(People people) {
					getView().hideNetworkLoading();
				}
			});
		mCompositeSubscription.add(peopleNetworkUpdateSubscription);
	}

	private LandView getView() {
		return mLandView.get();
	}

	@Override
	public void unbindView() {
		if (mCompositeSubscription.hasSubscriptions()) {
			mCompositeSubscription.unsubscribe();
		}
	}

	@Override
	public void cancelLoading() {
		mPeopleDataManager.cancelUpdate();
		mRealm.close();
	}
}
