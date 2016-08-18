package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by Will on 14/08/2016.
 *
 * Deletes all the current data from the Realm that's returned by the passed
 * {@link RealmFetcher} and copies the new updated data to it
 */
public class ReplaceRealmUpdater<UPDATETYPE extends RealmModel> extends RealmUpdater<UPDATETYPE> {

	private RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceRealmUpdater(Realm realm, RealmFetcher<UPDATETYPE> realmFetcher) {
		super(realm);
		mRealmFetcher = realmFetcher;
	}

	@Override
	public void updateRealm(Realm realm, UPDATETYPE updatedData) {
		mRealmFetcher.fetch(realm).deleteAllFromRealm();
		realm.copyToRealmOrUpdate(updatedData);
	}
}
