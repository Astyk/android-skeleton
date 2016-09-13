package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder.Source.NETWORK;

/**
 * Created by Will on 14/08/2016.
 */
// TODO: To use this we can use the AllRealmFetcher but apply a function that only gets one item from it.
// If it's not necessary, perhaps we can delete this.
public class NetworkFetchAndUpdateItem<RETURNTYPE extends RealmModel> {

	private Observable<RETURNTYPE> mRetrofitObservable;
	private RealmUpdater<RETURNTYPE> mRealmUpdater;

	public NetworkFetchAndUpdateItem(@NonNull Observable<RETURNTYPE> networkCall, @NonNull RealmUpdater<RETURNTYPE> realmUpdater) {
		mRetrofitObservable = networkCall;
		mRealmUpdater = realmUpdater;
	}

	public Observable<ResponseHolder<RETURNTYPE>> fetchAndUpdateData() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnNext(new Action1<RETURNTYPE>() {
				@Override
				public void call(RETURNTYPE returnData) {
					mRealmUpdater.update(returnData);
				}
			})
			.map(new Func1<RETURNTYPE, ResponseHolder<RETURNTYPE>>() {
				@Override
				public ResponseHolder<RETURNTYPE> call(RETURNTYPE data) {
					ResponseHolder<RETURNTYPE> responseHolder = new ResponseHolder<>(NETWORK);
					responseHolder.setData(data);
					return responseHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ResponseHolder<RETURNTYPE>>() {
				@Override
				public ResponseHolder<RETURNTYPE> call(Throwable throwable) {
					ResponseHolder<RETURNTYPE> responseHolder = new ResponseHolder<>(NETWORK);
					responseHolder.setError(throwable);
					return responseHolder;
				}
			});
	}

	public void cancelUpdate() {
		mRealmUpdater.cancel();
	}
}

