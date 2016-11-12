package com.github.willjgriff.skeleton.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Will on 15/09/2016.
 */

public abstract class RxFragment<PRESENTER extends RxPresenter> extends Fragment {

	private CompositeSubscription mCompositeSubscription;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// This approach seems to work, even when putting this Fragment into the BackStack, contrary
		// to the Android doc on setRetainInstance(). See here for why the doc may be incorrect:
		// https://www.reddit.com/r/androiddev/comments/34fb2m/retaininstancetrue_behavior_changed_in/
		// It could effect a Fragments lifecycle callbacks when it is in the BackStack, specifically
		// savedInstanceState may not be passed to onCreateView() upon recreation eg on orientation
		// change. I should keep an eye on this approach and review it when the SDK is updated.
		setRetainInstance(true);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mCompositeSubscription = new CompositeSubscription();
	}

	@Override
	public void onDestroyView() {
		mCompositeSubscription.unsubscribe();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		getPresenter().close();
		super.onDestroy();
	}

	protected abstract PRESENTER getPresenter();

	public void addSubscription(Subscription subscription) {
		mCompositeSubscription.add(subscription);
	}
}
