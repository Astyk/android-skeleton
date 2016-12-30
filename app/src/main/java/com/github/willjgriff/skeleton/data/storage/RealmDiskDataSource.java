package com.github.willjgriff.skeleton.data.storage;

import com.github.willjgriff.skeleton.data.models.converters.StorageConverter;
import com.github.willjgriff.skeleton.data.storage.realmfetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.BasicAsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.ReplaceListRealmUpdateMethod;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import rx.Observable;

/**
 * Created by Will on 23/12/2016.
 */

public class RealmDiskDataSource<DOMAINTYPE, STORAGETYPE extends RealmModel, QUERY>
	implements ListDiskDataSource<DOMAINTYPE, QUERY> {

	private Realm mRealm;
	private AllRealmFetcher<STORAGETYPE> mPersonAllRealmFetcher;
	private StorageConverter<STORAGETYPE, DOMAINTYPE> mStorageConverter;

	public RealmDiskDataSource(Realm realm, Class<STORAGETYPE> classType,
	                           StorageConverter<STORAGETYPE, DOMAINTYPE> storageConverter) {
		mRealm = realm;
		mPersonAllRealmFetcher = new AllRealmFetcher<>(classType);
		mStorageConverter = storageConverter;
	}

	@Override
	public Observable<List<DOMAINTYPE>> getFromStorage(QUERY peopleQuery) {
		return mPersonAllRealmFetcher.getAsyncObservable(mRealm)
			.first()
			.map(mStorageConverter.getConvertToDomainFunc());
	}

	@Override
	public void saveToStorage(List<DOMAINTYPE> domainList) {
		List<STORAGETYPE> storageList = mStorageConverter.getConvertFromDomainFunc().call(domainList);

		RealmUpdateMethod<List<STORAGETYPE>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPersonAllRealmFetcher);
		RealmUpdater<List<STORAGETYPE>> realmUpdater = new BasicAsyncRealmUpdater<>(mRealm, realmUpdateMethod);

		realmUpdater.update(storageList);
	}

	@Override
	public void close() {
		mRealm.close();
	}
}
