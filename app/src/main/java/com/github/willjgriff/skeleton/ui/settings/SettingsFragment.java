package com.github.willjgriff.skeleton.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	PublishSubject<Void> allTrigger = PublishSubject.create();
	PublishSubject<Void> networkTrigger = PublishSubject.create();

	Observable<Integer> cache = Observable.from(new Integer[]{1, 2, 3});
	Observable<Integer> network = Observable.from(new Integer[]{4, 5, 6});

	Observable<Integer> allWithTrigger = Observable.concat(cache, getNetworkTrigger());

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getAllTrigger().subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				cache.subscribe(integer -> {
					Log.i("RXIN", "Integero2: " + integer);
				});
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer integer) {
				Log.i("RXIN", "Integero: " + integer);
			}
		});

		allTrigger.onNext(null);
		networkTrigger.onNext(null);
	}

	private Observable<Integer> getAllTrigger() {
		return allTrigger.flatMap(new Func1<Void, Observable<Integer>>() {
			@Override
			public Observable<Integer> call(Void aVoid) {
				return allWithTrigger;
			}
		});
	}

	private Observable<Integer> getNetworkTrigger() {
		return networkTrigger.flatMap(new Func1<Void, Observable<Integer>>() {
			@Override
			public Observable<Integer> call(Void aVoid) {
				return network;
			}
		});
	}
}
