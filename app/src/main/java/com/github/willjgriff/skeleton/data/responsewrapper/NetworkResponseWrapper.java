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
			// Put data into ErrorHolder, ErrorHolder is necessary to pass the
			// error to where it can be used, if necessary.
			.map(new Func1<List<RESPONSETYPE>, ResponseHolder<List<RESPONSETYPE>>>() {
				@Override
				public ResponseHolder<List<RESPONSETYPE>> call(List<RESPONSETYPE> persons) {
					ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(NETWORK);
					responseHolder.setData(persons);
					return responseHolder;
				}
			})
			// Put any error into ErrorHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
			.onErrorReturn(new Func1<Throwable, ResponseHolder<List<RESPONSETYPE>>>() {
				@Override
				public ResponseHolder<List<RESPONSETYPE>> call(Throwable throwable) {
					ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(NETWORK);
					responseHolder.setError(throwable);
					return responseHolder;
				}
			});
	}
}
