package com.github.willjgriff.skeleton.ui.people2.di;

import com.github.willjgriff.skeleton.di.app.AppComponent;
import com.github.willjgriff.skeleton.ui.people.di.FragmentScope;
import com.github.willjgriff.skeleton.ui.people2.People2Fragment;

import dagger.Component;

/**
 * Created by Will on 23/12/2016.
 */

@FragmentScope
@Component(modules = People2Module.class, dependencies = AppComponent.class)
public interface People2Component {

	void inject(People2Fragment people2Fragment);
}
