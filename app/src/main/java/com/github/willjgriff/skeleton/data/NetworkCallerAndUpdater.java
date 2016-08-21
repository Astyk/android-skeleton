package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import io.realm.RealmModel;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class NetworkCallerAndUpdater<RETURNTYPE extends RealmModel> {

	private Observable<RETURNTYPE> mRetrofitObservable;
	private NewDataListener<RETURNTYPE> mNewDataListener;
	private RealmUpdater<RETURNTYPE> mRealmUpdater;
	private Subscription mSubscription;

	public NetworkCallerAndUpdater(@NonNull Observable<RETURNTYPE> networkCall, @NonNull NewDataListener<RETURNTYPE> newDataListener, @NonNull RealmUpdater<RETURNTYPE> realmUpdater) {
		mRetrofitObservable = networkCall;
		mNewDataListener = newDataListener;
		mRealmUpdater = realmUpdater;
	}

	public void fetchAndUpdateData() {

		mSubscription = mRetrofitObservable
			// Maybe cache is useful for saving during orientation changes?
			.cache()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Subscriber<RETURNTYPE>() {
				@Override
				public void onCompleted() {

				}

				@Override
				public void onError(Throwable e) {
					notifyListenerRequestFailed(e);
				}

				@Override
				public void onNext(RETURNTYPE returnData) {
					notifyListenerWithNewData(returnData);
					updateRealmWithNewData(returnData);
				}
			});
	}

	private void notifyListenerRequestFailed(Throwable t) {
		mNewDataListener.requestFailed(t);
	}

	private void notifyListenerWithNewData(RETURNTYPE returnData) {
		mNewDataListener.newData(returnData);
	}

	private void updateRealmWithNewData(RETURNTYPE returnData) {
		mRealmUpdater.update(returnData);
	}

	public void close() {
		mRealmUpdater.close();
		mSubscription.unsubscribe();
	}

	public interface NewDataListener<RETURNTYPE> {

		void newData(RETURNTYPE returnData);

		void requestFailed(Throwable t);
	}
}

