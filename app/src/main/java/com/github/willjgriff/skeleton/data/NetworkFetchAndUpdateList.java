package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ApiResponse;
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.NETWORK;

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

	public Observable<List<REALMTYPE>> getNetworkObservable() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.filter(listApiResponse -> listApiResponse.getContent() != null)
			// Unpack content from ApiResponse
			.map(ApiResponse::getContent)
			// Update Realm DB with new data
			.doOnNext(people -> mRealmUpdater.update(people));
	}

	public void cancelUpdate() {
		mRealmUpdater.cancel();
	}
}

