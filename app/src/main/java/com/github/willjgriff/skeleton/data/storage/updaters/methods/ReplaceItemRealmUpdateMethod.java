package com.github.willjgriff.skeleton.data.storage.updaters.methods;

import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by Will on 11/09/2016.
 */

public class ReplaceItemRealmUpdateMethod<UPDATETYPE extends RealmModel & Timestamp> implements RealmUpdateMethod<UPDATETYPE> {

	private RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceItemRealmUpdateMethod(RealmFetcher<UPDATETYPE> realmFetcher) {
		mRealmFetcher = realmFetcher;
	}

	@Override
	public void updateRealm(Realm realm, UPDATETYPE updatedData) {
		// TODO: Add timestamp to updatedData before storing similar to ReplaceListRealmUpdateMethod
		// This could currently throw an IllegalStateException.
		mRealmFetcher.fetchCurrentData(realm).deleteAllFromRealm();
		realm.copyToRealmOrUpdate(updatedData);
	}
}
