package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;

import io.realm.Realm;

/**
 * Created by Will on 11/09/2016.
 */

public class RealmSyncUpdater<UPDATETYPE> extends RealmUpdater<UPDATETYPE> {

	public RealmSyncUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	public void update(final UPDATETYPE data) {
		mRealm.executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				mRealmUpdateMethod.updateRealm(realm, data);
			}
		});
	}

	@Override
	public void cancel() {
		// no-op TODO: Can we get rid of this somehow?
	}
}
