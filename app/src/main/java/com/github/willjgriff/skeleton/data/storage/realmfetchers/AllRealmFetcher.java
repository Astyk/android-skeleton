package com.github.willjgriff.skeleton.data.storage.realmfetchers;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Will on 14/08/2016.
 */
public class AllRealmFetcher<RETURNTYPE extends RealmModel> extends RealmFetcher<RETURNTYPE> {

	private Class<RETURNTYPE> mReturnClass;

	public AllRealmFetcher(Class<RETURNTYPE> returnClass) {
		mReturnClass = returnClass;
	}

	protected RealmQuery<RETURNTYPE> select(Realm realm) {
		return realm.where(mReturnClass);
	}

	@Override
	public RealmResults<RETURNTYPE> fetchCurrentData(Realm realm) {
		return select(realm).findAll();
	}

	@Override
	public Observable<RealmResults<RETURNTYPE>> getAsyncObservable(Realm realm) {
		return select(realm).findAllAsync()
			.asObservable()
			// This SubscribeOn ObserveOn may not be necessary
			// TODO: I think the below renders this not async.
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread())
			// Ensure data is valid and available
			.filter(RealmResults::isLoaded)
			.filter(RealmResults::isValid);
	}
}
