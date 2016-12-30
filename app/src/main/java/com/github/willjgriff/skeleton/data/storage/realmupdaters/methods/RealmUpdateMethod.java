package com.github.willjgriff.skeleton.data.storage.realmupdaters.methods;

import io.realm.Realm;

/**
 * Created by Will on 11/09/2016.
 */

public interface RealmUpdateMethod<UPDATETYPE> {

	void updateRealm(Realm realm, UPDATETYPE updatedData);
}
