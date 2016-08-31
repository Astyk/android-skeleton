package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.NetworkFetcher;
import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import javax.inject.Inject;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements BasePresenter {

	private QuestionsDataManager mQuestionsDataManager;
	private LandView mLandView;

	@Inject
	LandPresenter(QuestionsDataManager questionsDataManager, LandView landView) {
		mQuestionsDataManager = questionsDataManager;
		mLandView = landView;
	}

	@Override
	public void loadData() {
		final Questions questions = mQuestionsDataManager.getStackOverflowQuestions(new NetworkFetcher.NewDataListener<Questions>() {
			@Override
			public void newData(Questions returnData) {
				mLandView.setQuestions(returnData.getStackOverflowQuestions());
				mLandView.hideLoading();
			}

			@Override
			public void requestFailed(Throwable t) {
				mLandView.showError();
			}
		});

		if (questions != null) {
			mLandView.setQuestions(questions.getStackOverflowQuestions());
			mLandView.showNetworkLoading();
		} else {
			mLandView.showInitialLoading();
		}
	}

	@Override
	public void cancelLoad() {
		mQuestionsDataManager.cancelRequests();
	}

	public void closeDatabase() {
		mQuestionsDataManager.closeRealm();
	}
}
