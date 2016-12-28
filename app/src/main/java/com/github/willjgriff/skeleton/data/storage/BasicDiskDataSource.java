package com.github.willjgriff.skeleton.data.storage;

import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.BasicAsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.updaters.methods.ReplaceListRealmUpdateMethod;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Will on 23/12/2016.
 */

public class BasicDiskDataSource<TYPE extends RealmModel, QUERY>
	implements ListDiskDataSource<TYPE, QUERY> {

	private Realm mRealm;
	private AllRealmFetcher<TYPE> mPersonAllRealmFetcher;

	public BasicDiskDataSource(Realm realm, Class<TYPE> classType) {
		mRealm = realm;
		mPersonAllRealmFetcher = new AllRealmFetcher<>(classType);
	}

	@Override
	public Observable<RealmResults<TYPE>> getFromStorage(QUERY peopleQuery) {
		return mPersonAllRealmFetcher.getAsyncObservable(mRealm).first();
	}

	@Override
	public void saveToStorage(List<TYPE> newsList) {
		RealmUpdateMethod<List<TYPE>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPersonAllRealmFetcher);
		RealmUpdater<List<TYPE>> realmUpdater = new BasicAsyncRealmUpdater<>(mRealm, realmUpdateMethod);
		realmUpdater.update(newsList);
	}

	@Override
	public void close() {
		mRealm.close();
	}
}
