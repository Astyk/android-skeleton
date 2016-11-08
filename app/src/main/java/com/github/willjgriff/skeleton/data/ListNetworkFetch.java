package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.ApiResponse;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class ListNetworkFetch<RESPONSETYPE> {

	private Observable<ApiResponse<List<RESPONSETYPE>>> mRetrofitObservable;

	public ListNetworkFetch(@NonNull Observable<ApiResponse<List<RESPONSETYPE>>> networkCall) {
		mRetrofitObservable = networkCall;
	}

	public Observable<List<RESPONSETYPE>> getNetworkObservable() {

		return mRetrofitObservable
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.filter(listApiResponse -> listApiResponse.getContent() != null)
			// TODO: Use a custom Retrofit Adapter for this I think...
			.map(ApiResponse::getContent);
	}

}

