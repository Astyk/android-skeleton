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

public class RxFragment extends Fragment {

	private CompositeSubscription mCompositeSubscription;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	public void addSubscription(Subscription subscription) {
		mCompositeSubscription.add(subscription);
	}

}
