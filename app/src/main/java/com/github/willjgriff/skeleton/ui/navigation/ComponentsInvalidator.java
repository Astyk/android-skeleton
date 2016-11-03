package com.github.willjgriff.skeleton.ui.navigation;

import com.github.willjgriff.skeleton.di.ComponentInvalidator;
import com.github.willjgriff.skeleton.ui.people.di.PeopleInjector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 03/10/2016.
 *
 * TODO: Maintaining and invalidating components is probably unnecessary optimisation. The
 * alternative being to not keep components in a Singleton at all and let them be GC'd.
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
