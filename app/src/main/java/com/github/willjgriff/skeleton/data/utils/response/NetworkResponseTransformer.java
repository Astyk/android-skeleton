package com.github.willjgriff.skeleton.data.utils.response;

import rx.Observable;
import timber.log.Timber;

import static com.github.willjgriff.skeleton.data.utils.response.ResponseHolder.Source.NETWORK;

/**
 * Created by Will on 03/11/2016.
 */

public class NetworkResponseTransformer<RESPONSETYPE> implements Observable.Transformer<RESPONSETYPE, ResponseHolder<RESPONSETYPE>> {

	@Override
	public Observable<ResponseHolder<RESPONSETYPE>> call(Observable<RESPONSETYPE> observable) {
		return observable
			// Put data into ResponseHolder, ResponseHolder is necessary to differentiate
			// between storage and network sourced data and retain any errors.
			.map(responseData -> new ResponseHolder<RESPONSETYPE>(NETWORK).setData(responseData));
			// Put any error into ResponseHolder so it is passed to onNext like
			// any other response. Then the error can be relayed to the user.
//			.onErrorReturn(throwable -> {
//				Timber.e(throwable, "Error getting data from Network");
//				return new ResponseHolder<RESPONSETYPE>(NETWORK).setError(throwable);
//			});
	}
}
