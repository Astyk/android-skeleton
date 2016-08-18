package com.github.willjgriff.skeleton.di.components;

import com.github.willjgriff.skeleton.di.QuestionsScope;
import com.github.willjgriff.skeleton.di.modules.QuestionsModule;
import com.github.willjgriff.skeleton.ui.land.LandFragment;

import dagger.Component;

/**
 * Created by Will on 18/08/2016.
 */
@QuestionsScope
@Component(modules = QuestionsModule.class, dependencies = AppComponent.class)
public interface QuestionsComponent {

	void inject(LandFragment fragment);

}
