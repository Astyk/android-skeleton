package com.github.willjgriff.skeleton.data.storage.fetchers;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Will on 14/08/2016.
 */

public class AllRealmFetcher<RETURNTYPE extends RealmModel> extends RealmFetcher<RETURNTYPE> {

	private Class<RETURNTYPE> mReturnClass;
	private Realm mRealm;

	public AllRealmFetcher(Class<RETURNTYPE> returnClass, Realm realm) {
		mReturnClass = returnClass;
		mRealm = realm;
	}

	protected RealmQuery<RETURNTYPE> select() {
		return mRealm.where(mReturnClass);
	}

	@Override
	public RealmResults<RETURNTYPE> fetch() {
		return select().findAll();
	}

	@Override
	public Observable<ErrorHolder<RETURNTYPE>> fetchObservable() {

		return select().findAllAsync()
			.asObservable()
			.filter(new Func1<RealmResults<RETURNTYPE>, Boolean>() {
				@Override
				public Boolean call(RealmResults<RETURNTYPE> RETURNTYPE) {
					return RETURNTYPE.isLoaded();
				}
			})
			.filter(new Func1<RealmResults<RETURNTYPE>, Boolean>() {
				@Override
				public Boolean call(RealmResults<RETURNTYPE> RETURNTYPE) {
					return RETURNTYPE.isValid();
				}
			})
			.first()
			.map(new Func1<RealmResults<RETURNTYPE>, RETURNTYPE>() {
				@Override
				public RETURNTYPE call(RealmResults<RETURNTYPE> RETURNTYPE) {
					return RETURNTYPE.get(0);
				}
			})
			.map(new Func1<RETURNTYPE, ErrorHolder<RETURNTYPE>>() {
				@Override
				public ErrorHolder<RETURNTYPE> call(RETURNTYPE RETURNTYPE) {
					ErrorHolder<RETURNTYPE> errorHolder = new ErrorHolder<>();
					errorHolder.setData(RETURNTYPE);
					return errorHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ErrorHolder<RETURNTYPE>>() {
				@Override
				public ErrorHolder<RETURNTYPE> call(Throwable throwable) {
					ErrorHolder<RETURNTYPE> errorHolder = new ErrorHolder<>();
					errorHolder.setError(throwable);
					return errorHolder;
				}
			});

	}
}
