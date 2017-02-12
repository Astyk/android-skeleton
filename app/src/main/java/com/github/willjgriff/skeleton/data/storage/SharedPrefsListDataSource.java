package com.github.willjgriff.skeleton.data.storage;

import com.github.willjgriff.skeleton.data.storage.sharedprefs.SharedPreferencesManager;
import com.github.willjgriff.skeleton.data.utils.transformers.BasicScheduleTransformer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Will on 30/12/2016.
 */

public abstract class SharedPrefsListDataSource<TYPE, QUERY> implements ListDiskDataSource<TYPE, QUERY> {

	private SharedPreferencesManager mSharedPreferencesManager;

	public SharedPrefsListDataSource(SharedPreferencesManager sharedPreferencesManager) {
		mSharedPreferencesManager = sharedPreferencesManager;
	}

	@Override
	public Observable<List<TYPE>> getFromStorage(QUERY query) {
		List<TYPE> dataList = mSharedPreferencesManager.readComplexObjectFromPreferences(getKey(), getTypeToken());
		return Observable.just(dataList);
	}

	@Override
	public Observable<List<TYPE>> saveToStorage(List<TYPE> dataList) {
		return Observable
			.create((Subscriber<? super List<TYPE>> subscriber) -> {
				try {
					mSharedPreferencesManager.writeObjectToPreferences(getKey(), dataList);
					subscriber.onNext(dataList);
					subscriber.onCompleted();
				} catch (Exception exception) {
					subscriber.onError(exception);
				}
			}).compose(new BasicScheduleTransformer<>());
	}

	protected abstract String getKey();

	protected abstract TypeToken<List<TYPE>> getTypeToken();
}
