package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmAsyncUpdater;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class NetworkFetchAndUpdateList<RETROFITTYPE, REALMTYPE extends RealmModel> {

	private Observable<RETROFITTYPE> mRetrofitObservable;
	private RealmAsyncUpdater<List<REALMTYPE>> mRealmUpdater;
	private Func1<RETROFITTYPE, List<REALMTYPE>> mRetrofitToRealmFunc;

	public NetworkFetchAndUpdateList(@NonNull Observable<RETROFITTYPE> networkCall,
	                                 @NonNull RealmAsyncUpdater<List<REALMTYPE>> realmUpdater,
	                                 @NonNull Func1<RETROFITTYPE, List<REALMTYPE>> retrofitToRealmFunc) {
		mRetrofitObservable = networkCall;
		mRealmUpdater = realmUpdater;
		mRetrofitToRealmFunc = retrofitToRealmFunc;
	}

	public Observable<ErrorHolder<List<REALMTYPE>>> getNetworkObservable() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.map(mRetrofitToRealmFunc)
			.doOnNext(new Action1<List<REALMTYPE>>() {
				@Override
				public void call(List<REALMTYPE> people) {
					mRealmUpdater.update(people);
				}
			})
			.map(new Func1<List<REALMTYPE>, ErrorHolder<List<REALMTYPE>>>() {
				@Override
				public ErrorHolder<List<REALMTYPE>> call(List<REALMTYPE> persons) {
					ErrorHolder<List<REALMTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setData(persons);
					return errorHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ErrorHolder<List<REALMTYPE>>>() {
				@Override
				public ErrorHolder<List<REALMTYPE>> call(Throwable throwable) {
					ErrorHolder<List<REALMTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setError(throwable);
					return errorHolder;
				}
			});
	}

	public void cancelUpdate() {
		mRealmUpdater.cancelUpdate();
	}
}

