package com.github.willjgriff.skeleton.data.storage.fetchers;

import com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder;
import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static com.github.willjgriff.skeleton.data.models.helpers.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 14/08/2016.
 */
// TODO: This is now coupled to the Timestamp interface. See if we can find a way to avoid this.
public class AllRealmFetcher<RETURNTYPE extends RealmModel & Timestamp> extends RealmFetcher<RETURNTYPE> {

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

	// TODO: This shouldn't return an Observable of ResponseHolder
	@Override
	public Observable<ResponseHolder<List<RETURNTYPE>>> fetchAsyncObservable() {

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
			.map(new Func1<List<RETURNTYPE>, ResponseHolder<List<RETURNTYPE>>>() {
				@Override
				public ResponseHolder<List<RETURNTYPE>> call(List<RETURNTYPE> data) {
					ResponseHolder<List<RETURNTYPE>> responseHolder = new ResponseHolder<>(STORAGE);
					responseHolder.setData(data);
					return responseHolder;
				}
			})
			// Put any error into ErrorHolder so it is passed to onNext like any other response.
			// Then the error can be relayed to the user.
			.onErrorReturn(new Func1<Throwable, ResponseHolder<List<RETURNTYPE>>>() {
				@Override
				public ResponseHolder<List<RETURNTYPE>> call(Throwable throwable) {
					ResponseHolder<List<RETURNTYPE>> responseHolder = new ResponseHolder<>(STORAGE);
					responseHolder.setError(throwable);
					return responseHolder;
				}
			});

	}
}
