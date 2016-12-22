package com.github.willjgriff.skeleton.data.utils.transformers;

import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;

import rx.Observable;

/**
 * Created by Will on 03/11/2016.
 *
 * TODO: This approach to filtering data from Storage after Network data has been fetched
 * works. However, ideally I would find an alternative that removes the state entirely.
 */
public class TakeUntilNetwork<RESPONSEHOLDER extends ResponseHolder> implements Observable.Transformer<RESPONSEHOLDER, RESPONSEHOLDER> {

	private boolean mNetworkDataFetched = false;

	@Override
	public Observable<RESPONSEHOLDER> call(Observable<RESPONSEHOLDER> observable) {
		return observable
			.doOnNext(responseholder -> {
				if (ResponseHolder.Source.NETWORK == responseholder.getSource() && !responseholder.hasError()) {
					mNetworkDataFetched = true;
				}
			})
			.filter(responseholder
				-> !(ResponseHolder.Source.STORAGE == responseholder.getSource() && mNetworkDataFetched));
	}
}
