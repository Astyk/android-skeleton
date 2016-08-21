package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.questions.QuestionsInjector;
import com.github.willjgriff.skeleton.di.questions.QuestionsModule;
import com.github.willjgriff.skeleton.ui.land.LandContract;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector {

	INSTANCE;

	private LandComponent mLandComponent;

	public LandComponent getComponent(LandContract.View landView) {
		if (mLandComponent == null) {
			mLandComponent = DaggerLandComponent.builder()
				.questionsComponent(QuestionsInjector.INSTANCE.getComponent())
				.landModule(new LandModule(landView))
				.build();
		}
		return mLandComponent;
	}

	// TODO: Remember to call this when going to a different screen, this will ensure it's recreated
	// when coming back to the LandScreen.
	public void invalidate() {
		mLandComponent = null;
	}

}
