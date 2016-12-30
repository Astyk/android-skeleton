package com.github.willjgriff.skeleton.data.storage;

import com.github.willjgriff.skeleton.data.storage.sharedprefs.SharedPreferencesManager;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import rx.Observable;

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
	public void saveToStorage(List<TYPE> newsList) {
		mSharedPreferencesManager.writeObjectToPreferences(getKey(), newsList);
	}

	@Override
	public void close() {

	}

	protected abstract String getKey();

	protected abstract TypeToken<List<TYPE>> getTypeToken();
}
