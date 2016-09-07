package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter<LandView> {

	private QuestionsDataManager mQuestionsDataManager;
	private WeakReference<LandView> mLandView;
	private Subscription mQuestionsCacheSubscription;
	private Subscription mQuestionsNetworkSubscription;
	private Realm mRealm;

	@Inject
	LandPresenter(QuestionsDataManager questionsDataManager) {
		mQuestionsDataManager = questionsDataManager;
		// TODO: Inject into DataManager
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public void bindView(LandView view) {
		mLandView = new WeakReference<>(view);

		// TODO: Only load the data once
		if (mQuestionsCacheSubscription == null || mQuestionsCacheSubscription.isUnsubscribed()) {
			loadCachedQuestions();
		}

		if (mQuestionsNetworkSubscription == null || mQuestionsNetworkSubscription.isUnsubscribed()) {
			loadNetworkQuestions();
		}
	}

	@Override
	public void unbindView() {
		if (mQuestionsCacheSubscription != null && !mQuestionsCacheSubscription.isUnsubscribed()) {
			mQuestionsCacheSubscription.unsubscribe();
		}
		if (mQuestionsNetworkSubscription != null && !mQuestionsNetworkSubscription.isUnsubscribed()) {
			mQuestionsNetworkSubscription.unsubscribe();
		}
	}

	@Override
	public void cancelLoading() {
		mQuestionsDataManager.cancelUpdate();
		mRealm.close();
	}

	private void loadCachedQuestions() {
		getView().showInitialLoading();
		mQuestionsCacheSubscription = mQuestionsDataManager.getRealmQuestionsObservable(mRealm)
			.subscribe(new Action1<RealmResults<Questions>>() {
				@Override
				public void call(RealmResults<Questions> questionses) {
					getView().setQuestions(questionses.get(0).getStackOverflowQuestions());
					// TODO: Add this to some sort of doOnFirst filter.
					getView().hideInitialLoading();
				}
			});
	}

	private void loadNetworkQuestions() {
		getView().showNetworkLoading();
		mQuestionsNetworkSubscription = mQuestionsDataManager.updateQuestionsFromNetwork(mRealm).subscribe(new Subscriber<Questions>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				getView().showError();
				getView().hideNetworkLoading();
			}

			@Override
			public void onNext(Questions questions) {
				getView().hideNetworkLoading();
			}
		});
	}

	private LandView getView() {
		return mLandView.get();
	}
}
