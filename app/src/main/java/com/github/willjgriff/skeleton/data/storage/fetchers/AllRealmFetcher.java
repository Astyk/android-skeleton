package com.github.willjgriff.skeleton.data.storage.fetchers;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Will on 14/08/2016.
 */

public class AllRealmFetcher<RETURNTYPE extends RealmModel> implements RealmFetcher<RETURNTYPE> {

	private Class<RETURNTYPE> mReturnClass;

	public AllRealmFetcher(Class<RETURNTYPE> returnClass) {
		mReturnClass = returnClass;
	}

	@Override
	public RealmResults<RETURNTYPE> fetch(Realm realm) {
		return realm.where(mReturnClass).findAll();
	}
}
