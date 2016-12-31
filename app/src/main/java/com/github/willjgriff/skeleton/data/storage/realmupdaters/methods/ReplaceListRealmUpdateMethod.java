package com.github.willjgriff.skeleton.data.storage.realmupdaters.methods;

import com.github.willjgriff.skeleton.data.storage.realmfetchers.RealmFetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Will on 11/09/2016.
 */
public class ReplaceListRealmUpdateMethod<UPDATETYPE extends RealmModel> implements RealmUpdateMethod<List<UPDATETYPE>> {

	private RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceListRealmUpdateMethod(RealmFetcher<UPDATETYPE> realmFetcher) {
		mRealmFetcher = realmFetcher;
	}

	@Override
	public void updateRealm(Realm realm, List<UPDATETYPE> updatedData) {
		RealmResults<UPDATETYPE> results = mRealmFetcher.getData(realm);

		results.deleteAllFromRealm();

		realm.copyToRealm(updatedData);
	}
}
