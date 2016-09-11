package com.github.willjgriff.skeleton.data.storage.fetchers;

import com.github.willjgriff.skeleton.data.models.ErrorHolder;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
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
	public Observable<ErrorHolder<List<RETURNTYPE>>> getCacheObservable() {

		return select().findAllAsync()
			.asObservable()
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread())
			.filter(new Func1<RealmResults<RETURNTYPE>, Boolean>() {
				@Override
				public Boolean call(RealmResults<RETURNTYPE> data) {
					return data.isLoaded();
				}
			})
			.filter(new Func1<RealmResults<RETURNTYPE>, Boolean>() {
				@Override
				public Boolean call(RealmResults<RETURNTYPE> data) {
					return data.isValid();
				}
			})
			.first()
			.map(new Func1<List<RETURNTYPE>, ErrorHolder<List<RETURNTYPE>>>() {
				@Override
				public ErrorHolder<List<RETURNTYPE>> call(List<RETURNTYPE> data) {
					ErrorHolder<List<RETURNTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setData(data);
					return errorHolder;
				}
			})
			.onErrorReturn(new Func1<Throwable, ErrorHolder<List<RETURNTYPE>>>() {
				@Override
				public ErrorHolder<List<RETURNTYPE>> call(Throwable throwable) {
					ErrorHolder<List<RETURNTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setError(throwable);
					return errorHolder;
				}
			});

	}
}
