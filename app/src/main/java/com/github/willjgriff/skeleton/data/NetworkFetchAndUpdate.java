package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import io.realm.Realm;
import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class NetworkFetchAndUpdate<RETURNTYPE extends RealmModel> {

	private Observable<RETURNTYPE> mRetrofitObservable;
	private RealmUpdater<RETURNTYPE> mRealmUpdater;
	private Realm mRealm;

	public NetworkFetchAndUpdate(Realm realm, @NonNull Observable<RETURNTYPE> networkCall, @NonNull RealmUpdater<RETURNTYPE> realmUpdater) {
		mRetrofitObservable = networkCall;
		mRealmUpdater = realmUpdater;
		mRealm = realm;
	}

	public Observable<RETURNTYPE> fetchAndUpdateData() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext(new Action1<RETURNTYPE>() {
				@Override
				public void call(RETURNTYPE returnData) {
					updateRealmWithNewData(returnData);
				}
			});
	}

	private void updateRealmWithNewData(RETURNTYPE returnData) {
		mRealmUpdater.update(returnData);
	}

	// TODO: Where should this be called from?
	public void cancelRequests() {
		mRealmUpdater.cancelUpdate();
	}
}

