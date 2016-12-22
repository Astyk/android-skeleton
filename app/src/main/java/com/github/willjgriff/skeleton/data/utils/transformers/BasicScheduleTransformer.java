package com.github.willjgriff.skeleton.data.utils.transformers;

import java.lang.reflect.Type;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 22/12/2016.
 */

public class BasicScheduleTransformer<TYPE> implements Observable.Transformer<TYPE, TYPE> {

	@Override
	public Observable<TYPE> call(Observable<TYPE> typeObservable) {
		return typeObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.filter(data -> data != null);
	}
}
