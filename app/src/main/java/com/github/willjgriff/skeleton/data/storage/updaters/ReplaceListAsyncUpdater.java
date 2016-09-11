package com.github.willjgriff.skeleton.data.storage.updaters;

import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by Will on 10/09/2016.
 */
// TODO: See if we can replace List with Iterable.
public class ReplaceListAsyncUpdater<UPDATETYPE extends RealmModel> extends RealmAsyncUpdater<List<UPDATETYPE>> {

	RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceListAsyncUpdater(Realm realm, RealmFetcher<UPDATETYPE> realmFetcher) {
		super(realm);
		mRealmFetcher = realmFetcher;
	}

	@Override
	protected void updateRealm(Realm realm, List<UPDATETYPE> updatedData) {
		// TODO: Find or add uniquely identifying data on models to delete them AFTER we have updated the Realm
		// I think deleting stuff before updating the Realm causes an IllegalStateException
		mRealmFetcher.fetch().deleteAllFromRealm();
		realm.copyToRealmOrUpdate(updatedData);
	}
}
