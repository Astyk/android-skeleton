package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.ui.land.LandContract;
import com.github.willjgriff.skeleton.ui.land.LandPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 19/08/2016.
 */
@Module
public class LandModule {

	private final LandContract.View mLandView;

	public LandModule(LandContract.View landView) {
		mLandView = landView;
	}

	@Provides
	LandContract.View providesLandView() {
		return mLandView;
	}

	// We could do this instead of putting @Inject on the Constructor of the Presenter.
//	@Provides
//	@LandScope
//	LandContract.Presenter providesLandPresenter(QuestionsDataManager questionsDataManager) {
//		return new LandPresenter(questionsDataManager, mLandView);
//	}
}
