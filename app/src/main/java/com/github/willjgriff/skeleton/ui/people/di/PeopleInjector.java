package com.github.willjgriff.skeleton.ui.people.di;

import com.github.willjgriff.skeleton.di.ComponentInvalidator;
import com.github.willjgriff.skeleton.di.app.AppInjector;

/**
 * Created by Will on 21/08/2016.
 */
// TODO: Remove if remains unused. See Components Invalidator for explanation
public enum PeopleInjector implements ComponentInvalidator {

	INSTANCE;

	PeopleComponent mPeopleComponent;

	public PeopleComponent getComponent() {
		if (mPeopleComponent == null) {
			mPeopleComponent = DaggerPeopleComponent.builder()
				.appComponent(AppInjector.INSTANCE.getComponent())
				.peopleModule(new PeopleModule())
				.build();
		}
		return mPeopleComponent;
	}

	public void invalidate() {
		mPeopleComponent = null;
	}
}
