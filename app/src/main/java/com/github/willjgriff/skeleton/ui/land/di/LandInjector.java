package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.questions.QuestionsInjector;

/**
 * Created by Will on 21/08/2016.
 */

public enum LandInjector {

	INSTANCE;

	public LandComponent getComponent() {
		return DaggerLandComponent.builder()
			.questionsComponent(QuestionsInjector.INSTANCE.getComponent())
			.build();
	}
}
