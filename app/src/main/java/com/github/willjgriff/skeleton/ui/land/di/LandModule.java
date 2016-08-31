package com.github.willjgriff.skeleton.ui.land.di;

import com.github.willjgriff.skeleton.ui.land.LandView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Will on 19/08/2016.
 */
@Module
public class LandModule {

	private final LandView mLandView;

	public LandModule(LandView landView) {
		mLandView = landView;
	}

	@Provides
	LandView providesLandView() {
		return mLandView;
	}
}
