package com.github.willjgriff.skeleton.di.questions;

import com.github.willjgriff.skeleton.di.app.AppInjector;

/**
 * Created by Will on 19/08/2016.
 */

public enum QuestionsInjector {

	INSTANCE;

	// We create a new instance every time as we need a new Realm Object
	public QuestionsComponent getComponent() {
		return DaggerQuestionsComponent.builder()
			.appComponent(AppInjector.INSTANCE.getComponent())
			.questionsModule(new QuestionsModule())
			.build();
	}

}
