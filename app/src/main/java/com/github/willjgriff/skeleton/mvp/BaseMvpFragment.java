package com.github.willjgriff.skeleton.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Will on 12/11/2016.
 */

public abstract class BaseMvpFragment<VIEW, PRESENTER extends MvpPresenter<VIEW>> extends Fragment {

	private PRESENTER mPresenter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		// This approach seems to work, even when putting this Fragment into the BackStack, contrary
		// to the Android doc on setRetainInstance(). See here for why the doc may be incorrect:
		// https://www.reddit.com/r/androiddev/comments/34fb2m/retaininstancetrue_behavior_changed_in/
		// It could effect a Fragments lifecycle callbacks when it is in the BackStack, specifically
		// savedInstanceState may not be passed to onCreateView() upon recreation eg on orientation
		// change. I should keep an eye on this approach and review it when the SDK is updated.

		// This is primarily done so we know when the user is leaving the screen permanently, as
		// opposed to going into the backstack or orientation change. Then we can close the Realm
		// instance the Presenter uses. It also saves the Presenter. Ideally we would use Dagger to
		// store the Presenter until the user navigates away from this screen. Realm makes that difficult.

		// NOTE: I've thought of an alternative approach that doesn't require this. I should not need to
		// use setRetainInstance(true) in the future. It may have stopped working as expected too as we
		// seem to be re-instantiating Fragments when returning to them.
		setRetainInstance(true);

		super.onCreate(savedInstanceState);
	}

	// The callbacks used shouldn't matter, if using onViewCreated() and
	// onDestroyView() though we must use onCreateView() to bind views
	@Override
	public void onStart() {
		super.onStart();
		getPresenter().bindView(getMvpView());
	}

	@Override
	public void onStop() {
		getPresenter().unbindView();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		getPresenter().close();
		super.onDestroy();
	}

	protected PRESENTER getPresenter() {
		if (mPresenter == null) {
			mPresenter = createPresenter();
		}
		return mPresenter;
	}

	protected abstract VIEW getMvpView();

	protected abstract PRESENTER createPresenter();
}
