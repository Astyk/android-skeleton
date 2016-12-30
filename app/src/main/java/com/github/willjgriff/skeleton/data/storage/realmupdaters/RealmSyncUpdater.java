package com.github.willjgriff.skeleton.data.storage.realmupdaters;

import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.RealmUpdateMethod;

import io.realm.Realm;

/**
 * Created by Will on 11/09/2016.
 */

public class RealmSyncUpdater<UPDATETYPE> extends RealmUpdater<UPDATETYPE> {

	public RealmSyncUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	public void update(final UPDATETYPE data) {
		mRealm.executeTransaction(realm -> mRealmUpdateMethod.updateRealm(realm, data));
	}
}
