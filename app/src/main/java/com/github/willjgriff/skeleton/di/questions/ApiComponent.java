package com.github.willjgriff.skeleton.di.questions;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.RandomPeopleDataManager;
import com.github.willjgriff.skeleton.di.app.AppComponent;
import com.github.willjgriff.skeleton.ui.settings.SettingsFragment;

import dagger.Component;

/**
 * Created by Will on 18/08/2016.
 */
@FragmentScope
@Component(modules = ApiModule.class, dependencies = AppComponent.class)
public interface ApiComponent {

	RandomPeopleDataManager providesQuestionsDataManager();

}
