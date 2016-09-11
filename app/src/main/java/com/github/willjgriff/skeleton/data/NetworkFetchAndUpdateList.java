package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ApiResponse;
import com.github.willjgriff.skeleton.data.models.helpers.ErrorHolder;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmSyncUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

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
public class NetworkFetchAndUpdateList<REALMTYPE extends RealmModel> {

	private Observable<ApiResponse<List<REALMTYPE>>> mRetrofitObservable;
	private RealmUpdater<List<REALMTYPE>> mRealmUpdater;

	public NetworkFetchAndUpdateList(@NonNull Observable<ApiResponse<List<REALMTYPE>>> networkCall,
	                                 @NonNull RealmUpdater<List<REALMTYPE>> realmUpdater) {
		mRetrofitObservable = networkCall;
		mRealmUpdater = realmUpdater;
	}

	public Observable<ErrorHolder<List<REALMTYPE>>> getNetworkObservable() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			// Unpack content from ApiResponse
			.map(new Func1<ApiResponse<List<REALMTYPE>>, List<REALMTYPE>>() {
				@Override
				public List<REALMTYPE> call(ApiResponse<List<REALMTYPE>> apiResponse) {
					return apiResponse.getContent();
				}
			})
			// Update Realm DB with new data
			.doOnNext(new Action1<List<REALMTYPE>>() {
				@Override
				public void call(List<REALMTYPE> people) {
					mRealmUpdater.update(people);
				}
			})
			// Put data into ErrorHolder, ErrorHolder is necessary to pass the
			// error to where it can be used, if necessary.
			.map(new Func1<List<REALMTYPE>, ErrorHolder<List<REALMTYPE>>>() {
				@Override
				public ErrorHolder<List<REALMTYPE>> call(List<REALMTYPE> persons) {
					ErrorHolder<List<REALMTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setData(persons);
					return errorHolder;
				}
			})
			// Put any error into ErrorHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
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
		mRealmUpdater.cancel();
	}
}

