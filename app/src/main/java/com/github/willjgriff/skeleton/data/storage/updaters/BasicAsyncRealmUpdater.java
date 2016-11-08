package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import timber.log.Timber;

/**
 * Created by Will on 11/09/2016.
 */

public class BasicAsyncRealmUpdater<UPDATETYPE> extends AsyncRealmUpdater<UPDATETYPE> {

	private RealmAsyncTask mRealmAsyncTask;

	public BasicAsyncRealmUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	@Override
	public void update(final UPDATETYPE updatedData) {
		mRealmAsyncTask = mRealm.executeTransactionAsync(realm -> {
			mRealmUpdateMethod.updateRealm(realm, updatedData);
		}, error -> {
			Timber.e(error, "Error updating Realm");
		});
	}

	@Override
	public void cancel() {
		if (mRealmAsyncTask != null) {
			mRealmAsyncTask.cancel();
			mRealmAsyncTask = null;
		}
	}
}
