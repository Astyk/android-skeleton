package com.github.willjgriff.skeleton.data.responsewrapper;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 13/09/2016.
 */

public class RealmResponseWrapper<RESPONSETYPE extends RealmModel> {

	public Observable<ResponseHolder<List<RESPONSETYPE>>> wrap(Observable<RealmResults<RESPONSETYPE>> observable) {
		return observable
			// Only take the first emission. More data will be emitted if/when this RealmQuery's data is updated.
//			.first()
			// Put data into ResponseHolder, ResponseHolder is necessary to differentiate between
			// cache and network sourced data and retain any errors.
			.map(responsetypes -> {
				ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(STORAGE);
				responseHolder.setData(responsetypes);
				return responseHolder;
			})
			// Put any error into ResponseHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
			.onErrorReturn(throwable -> {
				ResponseHolder<List<RESPONSETYPE>> responseHolder = new ResponseHolder<>(STORAGE);
				responseHolder.setError(throwable);
				return responseHolder;
			});
	}
}
