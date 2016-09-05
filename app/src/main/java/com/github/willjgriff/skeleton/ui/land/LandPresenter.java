package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter<LandView> {

	private QuestionsDataManager mQuestionsDataManager;
	private WeakReference<LandView> mLandView;
	private Subscription mQuestionsUpdateSubscription;
	private Realm mRealm;

	@Inject
	LandPresenter(QuestionsDataManager questionsDataManager) {
		mQuestionsDataManager = questionsDataManager;
		mRealm = Realm.getDefaultInstance();
	}

	@Override
	public void bindView(LandView view) {
		mLandView = new WeakReference<>(view);

		if (mQuestionsUpdateSubscription == null || mQuestionsUpdateSubscription.isUnsubscribed()) {
			getView().showNetworkLoading();
			fetchQuestions();
		}

		mQuestionsDataManager.updateQuestionsFromNetwork(mRealm);
	}

	private LandView getView() {
		return mLandView.get();
	}

	private void fetchQuestions() {
		mQuestionsUpdateSubscription = mQuestionsDataManager.getQuestionsUpdateObservable().subscribe(new Subscriber<Questions>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable e) {
				getView().showError();
			}

			@Override
			public void onNext(Questions questions) {
				getView().setQuestions(questions.getStackOverflowQuestions());
				getView().hideLoading();
			}
		});

		Questions questions = mQuestionsDataManager.getStoredQuestions(mRealm, new RealmChangeListener<RealmResults<Questions>>() {
			@Override
			public void onChange(RealmResults<Questions> element) {
//				getView().setQuestions(element.get(0).getStackOverflowQuestions());
//				getView().hideLoading();
			}
		});

		if (questions != null) {
			getView().setQuestions(questions.getStackOverflowQuestions());
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
		if (mQuestionsUpdateSubscription != null) {
			mQuestionsUpdateSubscription.unsubscribe();
		}
		mQuestionsDataManager.cancelUpdate();
		mRealm.close();
	}
}
