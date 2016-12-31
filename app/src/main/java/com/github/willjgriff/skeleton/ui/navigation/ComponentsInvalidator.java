package com.github.willjgriff.skeleton.ui.navigation;

import com.github.willjgriff.skeleton.di.ComponentInvalidator;
import com.github.willjgriff.skeleton.ui.people.di.PeopleInjector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 03/10/2016.
 *
 * TODO: Remove if unused. This would be used if we want to cache Presenters using Dagger
 * during orientation change and back stack processing. Then invalidating them when we move
 * navigation entries.
 */
public class ComponentsInvalidator {

	private List<ComponentInvalidator> mDaggerInjectors = new ArrayList<ComponentInvalidator>() {
		{
			add(PeopleInjector.INSTANCE);
		}
	};

	public void invalidateComponents() {
		for (ComponentInvalidator injector : mDaggerInjectors) {
			injector.invalidate();
		}
	}
}
