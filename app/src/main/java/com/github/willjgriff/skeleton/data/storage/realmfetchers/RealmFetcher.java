package com.github.willjgriff.skeleton.data.storage.realmfetchers;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Will on 14/08/2016.
 */

public abstract class RealmFetcher<RETURNTYPE extends RealmModel> {

	protected abstract RealmQuery<RETURNTYPE> select(Realm realm);

	public abstract RealmResults<RETURNTYPE> getData(Realm realm);

	public abstract RealmResults<RETURNTYPE> getDataAsync(Realm realm);

	public abstract Observable<RealmResults<RETURNTYPE>> getDataAsyncObservable(Realm realm);

}
