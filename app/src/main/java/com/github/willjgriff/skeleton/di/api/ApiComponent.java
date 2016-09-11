package com.github.willjgriff.skeleton.di.api;

import com.github.willjgriff.skeleton.ui.land.PeopleDataManager;
import com.github.willjgriff.skeleton.di.app.AppComponent;

import dagger.Component;

/**
 * Created by Will on 18/08/2016.
 */
@FragmentScope
@Component(modules = ApiModule.class, dependencies = AppComponent.class)
public interface ApiComponent {

	PeopleDataManager providesRandomersDataManager();

}
