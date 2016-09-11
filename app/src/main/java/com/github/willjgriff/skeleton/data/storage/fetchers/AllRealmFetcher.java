package com.github.willjgriff.skeleton.data.storage.fetchers;

import com.github.willjgriff.skeleton.data.models.helpers.ErrorHolder;
import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;

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
// TODO: This is now coupled to the Timestamp interface. See if we can find a way to avoid this.
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
	public RealmResults<RETURNTYPE> fetchCurrentData() {
		return select().lessThan(Timestamp.TIMESTAMP_FIELD, System.currentTimeMillis()).findAll();
	}

	@Override
	public Observable<ErrorHolder<List<RETURNTYPE>>> fetchAsyncObservable() {

		return select().findAllAsync()
			.asObservable()
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread())
			// Ensure data is valid and available
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
			// Only take the first emission. More data will be emitted if/when this RealmQuery's data is updated.
			.first()
			// Put data into ErrorHolder, ErrorHolder is necessary to pass
			// the error to where it can be used, if necessary.
			.map(new Func1<List<RETURNTYPE>, ErrorHolder<List<RETURNTYPE>>>() {
				@Override
				public ErrorHolder<List<RETURNTYPE>> call(List<RETURNTYPE> data) {
					ErrorHolder<List<RETURNTYPE>> errorHolder = new ErrorHolder<>();
					errorHolder.setData(data);
					return errorHolder;
				}
			})
			// Put any error into ErrorHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
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
