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
public class LandPresenter2 implements BasePresenter<LandView> {

	private RandomPeopleDataManager mRandomPeopleDataManager;
	private WeakReference<LandView> mLandView;
	private Subscription mQuestionsUpdateSubscription;
	private Realm mRealm;

	@Inject
	LandPresenter2(RandomPeopleDataManager randomPeopleDataManager) {
		mRandomPeopleDataManager = randomPeopleDataManager;
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public void bindView(LandView view) {
		mLandView = new WeakReference<>(view);

		if (mQuestionsUpdateSubscription == null || mQuestionsUpdateSubscription.isUnsubscribed()) {
			getView().showNetworkLoading();
			fetchQuestions();
		}

		mRandomPeopleDataManager.updateRandomersFromNetwork(mRealm);
	}

	private LandView getView() {
		return mLandView.get();
	}

	private void fetchQuestions() {
		mQuestionsUpdateSubscription = mRandomPeopleDataManager.getRandomersUpdateObservable().subscribe(new Subscriber<People>() {
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

		People people = mRandomPeopleDataManager.getStoredRandomers(mRealm);

		if (people != null) {
			getView().setPeople(people.getPeople());
		} else {
			getView().showInitialLoading();
		}
	}

	@Override
	public void unbindView() {
		if (mQuestionsUpdateSubscription != null && !mQuestionsUpdateSubscription.isUnsubscribed()) {
			mQuestionsUpdateSubscription.unsubscribe();
		}
	}

	@Override
	public void cancelLoading() {
		mRandomPeopleDataManager.cancelUpdate();
		mRealm.close();
	}
}
