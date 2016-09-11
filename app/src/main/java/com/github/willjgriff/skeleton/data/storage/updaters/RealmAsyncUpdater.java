package com.github.willjgriff.skeleton.data.storage.updaters;

import android.util.Log;

import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Created by Will on 11/09/2016.
 */

public class RealmAsyncUpdater<UPDATETYPE> extends RealmUpdater<UPDATETYPE> {

	private RealmAsyncTask mRealmAsyncTask;

	public RealmAsyncUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	@Override
	public void update(final UPDATETYPE updatedData) {
		mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				mRealmUpdateMethod.updateRealm(realm, updatedData);
				Log.d("REALM", "Current thread inside Async: " + Thread.currentThread().toString());
			}
		}, new Realm.Transaction.OnError() {
			@Override
			public void onError(Throwable error) {
				Log.e("REALM", "Error updating Realm", error);
			}
		});
	}

	@Override
	public void cancel() {
		if(mRealmAsyncTask != null) {
			mRealmAsyncTask.cancel();
			mRealmAsyncTask = null;
		}
	}
}
