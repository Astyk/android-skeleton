package com.github.willjgriff.skeleton.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	private NavigationToolbarListener mNavigationToolbarListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mNavigationToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PublishSubject<Integer> publishSubject = PublishSubject.create();

		Observable<Integer> observable = publishSubject.asObservable().replay(1).autoConnect();

		observable.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				Timber.e("GOT AN ERROR");
			}

			@Override
			public void onNext(Integer o) {

			}
		});

		observable.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				Timber.e("ALSO GOT AN ERROR");
			}

			@Override
			public void onNext(Integer o) {

			}
		});

		publishSubject.onError(new Throwable());

	}

	@Override
	public void onResume() {
		super.onResume();
		mNavigationToolbarListener.setToolbarTitle(R.string.fragment_settings_title);
	}
}
