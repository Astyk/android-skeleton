package com.github.willjgriff.skeleton.data.storage.realmfetchers;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
	public RealmResults<RETURNTYPE> getData(Realm realm) {
		return select(realm).findAll();
	}

	@Override
	public RealmResults<RETURNTYPE> getDataAsync(Realm realm) {
		return select(realm).findAllAsync();
	}

	@Override
	public Observable<RealmResults<RETURNTYPE>> getDataAsyncObservable(Realm realm) {
		return select(realm).findAllAsync()
			.asObservable()
			// Ensure data is valid and available
			.filter(RealmResults::isLoaded)
			.filter(RealmResults::isValid);
	}
}
