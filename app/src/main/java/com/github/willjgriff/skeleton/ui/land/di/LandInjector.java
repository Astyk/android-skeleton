package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.questions.QuestionsInjector;
import com.github.willjgriff.skeleton.ui.land.LandView;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector {

	INSTANCE;

	public LandComponent getComponent(LandView landView) {
		return DaggerLandComponent.builder()
			.questionsComponent(QuestionsInjector.INSTANCE.getComponent())
			.landModule(new LandModule(landView))
			.build();
	}
}
