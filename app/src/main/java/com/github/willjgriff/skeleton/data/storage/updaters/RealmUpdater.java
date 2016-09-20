package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;

import io.realm.Realm;

/**
 * Created by Will on 11/09/2016.
 */

public abstract class RealmUpdater<UPDATETYPE> {

	protected Realm mRealm;
	protected RealmUpdateMethod<UPDATETYPE> mRealmUpdateMethod;

	public RealmUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		mRealm = realm;
		mRealmUpdateMethod = realmUpdateMethod;
	}

	public abstract void update(UPDATETYPE newData);

}
