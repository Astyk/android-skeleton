package com.github.willjgriff.skeleton.data.storage.updaters.methods;

import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

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
		// TODO: If used, consider some deletion behaviour before adding item to the Realm
//		mRealmFetcher.fetchCurrentData(realm).deleteAllFromRealm();
		realm.copyToRealmOrUpdate(updatedData);
	}
}
