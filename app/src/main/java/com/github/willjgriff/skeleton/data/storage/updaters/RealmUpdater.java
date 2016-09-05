package com.github.willjgriff.skeleton.data.storage.updaters;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmModel;

/**
 * Created by Will on 14/08/2016.
 * <p>
 * Encapsulates basic realm transaction functionality.
 * Abstracting this behaviour might be overkill. We'll see when we come to use it.
 */
public abstract class RealmUpdater<UPDATETYPE extends RealmModel> {

	private Realm mRealm;
	private RealmAsyncTask mRealmAsyncTask;

	public RealmUpdater(Realm realm) {
		mRealm = realm;
	}

	public void update(final UPDATETYPE updatedData) {
//		mRealm.executeTransaction(new Realm.Transaction() {
//			@Override
//			public void execute(Realm realm) {
//				updateRealm(realm, updatedData);
//				Log.i("REALM", "Current thread inside Async: " + Thread.currentThread().toString());
//			}
//		});

		mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				updateRealm(realm, updatedData);
				Log.i("REALM", "Current thread inside Async: " + Thread.currentThread().toString());
			}
		}, new Realm.Transaction.OnError() {
			@Override
			public void onError(Throwable error) {
				Log.e("REALM", "Error updating Realm", error);
			}
		});
	}

	protected abstract void updateRealm(Realm realm, UPDATETYPE updatedData);

	public void cancelUpdate() {
		if (mRealmAsyncTask != null) {
			mRealmAsyncTask.cancel();
			mRealmAsyncTask = null;
		}
	}

}
