package com.github.willjgriff.skeleton.ui.people.di;

import com.github.willjgriff.skeleton.di.app.AppComponent;
import com.github.willjgriff.skeleton.ui.people.PeopleFragment;

import dagger.Component;

/**
 * Created by Will on 19/08/2016.
 */
@PeopleScope
@Component(modules = PeopleModule.class, dependencies = AppComponent.class)
public interface PeopleComponent {

	void inject(PeopleFragment fragment);

}
