package com.github.willjgriff.skeleton.data.responsewrapper;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.NETWORK;

/**
 * Created by Will on 13/09/2016.
 */

public class NetworkResponseWrapper<RESPONSETYPE> {

	public Observable<ResponseHolder<List<RESPONSETYPE>>> wrap(Observable<List<RESPONSETYPE>> observable) {
		return observable
			// Put data into ResponseHolder, ResponseHolder is necessary to differentiate between
			// cache and network sourced data and retain any errors.
			.map(persons -> {
				ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(NETWORK);
				responseHolder.setData(persons);
				return responseHolder;
			})
			// Put any error into ResponseHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
			.onErrorReturn(throwable -> {
				ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(NETWORK);
				responseHolder.setError(throwable);
				return responseHolder;
			});
	}
}
