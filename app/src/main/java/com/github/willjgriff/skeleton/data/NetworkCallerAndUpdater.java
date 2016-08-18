package com.github.willjgriff.skeleton.data;

import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;

import io.realm.RealmModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Will on 14/08/2016.
 */
public class NetworkCallerAndUpdater<RETURNTYPE extends RealmModel> {

	private Call<RETURNTYPE> mRetrofitCall;
	private NewDataListener<RETURNTYPE> mNewDataListener;
	private RealmUpdater<RETURNTYPE> mRealmUpdater;

	public NetworkCallerAndUpdater(Call<RETURNTYPE> networkCall, NewDataListener<RETURNTYPE> newDataListener, RealmUpdater<RETURNTYPE> realmUpdater) {
		mRetrofitCall = networkCall;
		mNewDataListener = newDataListener;
		mRealmUpdater = realmUpdater;
	}

	public void fetchAndUpdateData() {
		mRetrofitCall.enqueue(new Callback<RETURNTYPE>() {
			@Override
			public void onResponse(Call<RETURNTYPE> call, Response<RETURNTYPE> response) {
				RETURNTYPE returnData = response.body();
				notifyListenerWithNewData(returnData);
				updateRealmWithNewData(returnData);
			}

			@Override
			public void onFailure(Call<RETURNTYPE> call, Throwable t) {
				notifyListenerRequestFailed(t);
			}
		});
	}

	private void notifyListenerWithNewData(RETURNTYPE returnData) {
		mNewDataListener.dataUpdated(returnData);
	}

	private void updateRealmWithNewData(RETURNTYPE returnData) {
		mRealmUpdater.update(returnData);
	}

	private void notifyListenerRequestFailed(Throwable t) {
		mNewDataListener.requestFailed(t);
	}

	public void close() {
		mRealmUpdater.close();
		if (mRetrofitCall != null) {
			mRetrofitCall.cancel();
			mRetrofitCall = null;
		}
	}

	public interface NewDataListener<RETURNTYPE> {
		void dataUpdated(RETURNTYPE returnData);

		void requestFailed(Throwable t);
	}
}

