package com.github.willjgriff.skeleton.data.storage.updaters.methods;

import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Will on 11/09/2016.
 */

public interface RealmUpdateMethod<UPDATETYPE> {

	void updateRealm(Realm realm, UPDATETYPE updatedData);
}
