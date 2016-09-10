package com.github.willjgriff.skeleton.data.storage.fetchers;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Will on 14/08/2016.
 */

public abstract class RealmFetcher<RETURNTYPE extends RealmModel> {

	protected abstract RealmQuery<RETURNTYPE> select();

	public abstract RealmResults<RETURNTYPE> fetch();

	public abstract Observable<ErrorHolder<RETURNTYPE>> fetchObservable();
}
