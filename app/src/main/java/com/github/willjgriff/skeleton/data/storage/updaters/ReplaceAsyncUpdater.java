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
public class ReplaceAsyncUpdater<UPDATETYPE extends RealmModel> extends RealmAsyncUpdater<UPDATETYPE> {

	private RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceAsyncUpdater(Realm realm, RealmFetcher<UPDATETYPE> realmFetcher) {
		super(realm);
		mRealmFetcher = realmFetcher;
	}

	@Override
	public void updateRealm(Realm realm, UPDATETYPE updatedData) {
		// TODO: Find or add uniquely identifying data on models to delete them AFTER we have updated the Realm
		// I think deleting stuff before updating the Realm causes an IllegalStateException
		mRealmFetcher.fetch().deleteAllFromRealm();
		realm.copyToRealmOrUpdate(updatedData);
	}
}