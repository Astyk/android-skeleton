package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.RandomPeopleDataManager;
import com.github.willjgriff.skeleton.data.models.People;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter<LandView> {

	private RandomPeopleDataManager mPeopleDataManager;
	private WeakReference<LandView> mLandView;
	private Subscription mPeopleUpdateSubscription;
	private Realm mRealm;

	@Inject
	LandPresenter(RandomPeopleDataManager peopleDataManager) {
		mPeopleDataManager = peopleDataManager;
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public void bindView(LandView view) {
		mLandView = new WeakReference<>(view);

		if (mPeopleUpdateSubscription == null || mPeopleUpdateSubscription.isUnsubscribed()) {
			getView().showNetworkLoading();
			fetchPeople();
		}

		mPeopleDataManager.updateRandomersFromNetwork(mRealm);
	}

	private LandView getView() {
		return mLandView.get();
	}

	private void fetchPeople() {
		mPeopleUpdateSubscription = mPeopleDataManager.getPeopleObservable().subscribe(new Subscriber<People>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				getView().showError();
				getView().hideLoading();
			}

			@Override
			public void onNext(People people) {
				getView().setPeople(people.getPeople());
				getView().hideLoading();
			}
		});

		People people = mPeopleDataManager.getStoredRandomers(mRealm);

		if (people != null) {
			getView().setPeople(people.getPeople());
		} else {
			getView().showInitialLoading();
		}
	}

	@Override
	public void unbindView() {
		if (mPeopleUpdateSubscription != null && !mPeopleUpdateSubscription.isUnsubscribed()) {
			mPeopleUpdateSubscription.unsubscribe();
		}
	}

	@Override
	public void cancelLoading() {
		mPeopleDataManager.cancelUpdate();
		mRealm.close();
	}
}
