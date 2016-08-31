package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.di.questions.QuestionsComponent;
import com.github.willjgriff.skeleton.ui.land.LandFragment;

import dagger.Component;

/**
 * Created by Will on 19/08/2016.
 */
@LandScope
@Component(dependencies = QuestionsComponent.class, modules = LandModule.class)
public interface LandComponent {

	void inject(LandFragment fragment);

}
