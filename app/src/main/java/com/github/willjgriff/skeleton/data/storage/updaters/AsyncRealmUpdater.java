package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;

import io.realm.Realm;

/**
 * Created by Will on 19/09/2016.
 */

public abstract class AsyncRealmUpdater<UPDATETYPE> extends RealmUpdater<UPDATETYPE> {

	public AsyncRealmUpdater(Realm realm, RealmUpdateMethod<UPDATETYPE> realmUpdateMethod) {
		super(realm, realmUpdateMethod);
	}

	public abstract void cancel();
}
