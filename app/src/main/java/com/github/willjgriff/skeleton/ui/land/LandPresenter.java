package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.NetworkCallerAndUpdater;
import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.ui.land.di.LandScope;

import javax.inject.Inject;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
public class LandPresenter implements LandContract.Presenter {

	QuestionsDataManager mQuestionsDataManager;
	LandContract.View mView;

	@Inject
	LandPresenter(QuestionsDataManager questionsDataManager, LandContract.View view) {
		mQuestionsDataManager = questionsDataManager;
		mView = view;
	}

	@Override
	public void start() {
		mView.showNetworkLoadingView();
		final Questions questions = mQuestionsDataManager.getStackOverflowQuestions(new NetworkCallerAndUpdater.NewDataListener<Questions>() {
			@Override
			public void dataUpdated(Questions returnData) {
				mView.setQuestions(returnData.getStackOverflowQuestions());
				mView.hideNetworkLoadingView();
			}

			@Override
			public void requestFailed(Throwable t) {

			}
		});

		if (questions != null) {
			mView.setQuestions(questions.getStackOverflowQuestions());
		}

	}

	@Override
	public void stop() {
		mQuestionsDataManager.close();
	}
}
