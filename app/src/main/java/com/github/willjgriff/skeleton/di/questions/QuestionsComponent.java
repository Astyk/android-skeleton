package com.github.willjgriff.skeleton.di.questions;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.di.app.AppComponent;
import com.github.willjgriff.skeleton.ui.land.LandFragment;

import dagger.Component;

/**
 * Created by Will on 18/08/2016.
 */
@QuestionsScope
@Component(modules = QuestionsModule.class, dependencies = AppComponent.class)
public interface QuestionsComponent {

	QuestionsDataManager providesQuestionsDataManager();

}
