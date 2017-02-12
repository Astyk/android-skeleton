package com.github.willjgriff.skeleton.data.storage.realmupdaters;

import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.RealmUpdateMethod;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by Will on 11/09/2016.
 */

public class AsyncRealmUpdater<UPDATETYPE> extends RealmUpdater<UPDATETYPE> {

	public AsyncRealmUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	@Override
	public void update(final UPDATETYPE updatedData) {
		mRealm.executeTransactionAsync(
			realm -> mRealmUpdateMethod.updateRealm(realm, updatedData),
			error -> Timber.e(error, "Error updating Realm with: " + updatedData.toString()));
	}
}
