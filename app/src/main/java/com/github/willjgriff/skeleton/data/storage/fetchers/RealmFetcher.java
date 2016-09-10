package com.github.willjgriff.skeleton.data.storage.fetchers;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Will on 14/08/2016.
 */

public interface RealmFetcher<RETURNTYPE extends RealmModel> {

	RealmResults<RETURNTYPE> fetch(Realm realm);

	RealmResults<RETURNTYPE> fetchAsync(Realm realm);
}
