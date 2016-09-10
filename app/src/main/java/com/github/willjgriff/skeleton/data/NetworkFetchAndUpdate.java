package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import io.realm.Realm;
import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class NetworkFetchAndUpdate<RETURNTYPE extends RealmModel> {

	private Observable<RETURNTYPE> mRetrofitObservable;
	private RealmUpdater<RETURNTYPE> mRealmUpdater;

	public NetworkFetchAndUpdate(@NonNull Observable<RETURNTYPE> networkCall, @NonNull RealmUpdater<RETURNTYPE> realmUpdater) {
		mRetrofitObservable = networkCall;
		mRealmUpdater = realmUpdater;
	}

	public Observable<ErrorHolder<RETURNTYPE>> fetchAndUpdateData() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext(new Action1<RETURNTYPE>() {
				@Override
				public void call(RETURNTYPE returnData) {
					mRealmUpdater.update(returnData);
				}
			})
			.map(new Func1<RETURNTYPE, ErrorHolder<RETURNTYPE>>() {
				@Override
				public ErrorHolder<RETURNTYPE> call(RETURNTYPE data) {
					ErrorHolder<RETURNTYPE> errorHolder = new ErrorHolder<RETURNTYPE>();
					errorHolder.setData(data);
					return errorHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ErrorHolder<RETURNTYPE>>() {
				@Override
				public ErrorHolder<RETURNTYPE> call(Throwable throwable) {
					ErrorHolder<RETURNTYPE> errorHolder = new ErrorHolder<RETURNTYPE>();
					errorHolder.setError(throwable);
					return errorHolder;
				}
			});
	}

	public void cancelUpdate() {
		mRealmUpdater.cancelUpdate();
	}
}

