package com.github.willjgriff.skeleton.data.storage;

import com.github.willjgriff.skeleton.data.models.converters.StorageDataConverter;
import com.github.willjgriff.skeleton.data.storage.realmfetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.AsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.SyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.ReplaceListRealmUpdateMethod;
import com.github.willjgriff.skeleton.data.utils.transformers.BasicScheduleTransformer;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Will on 23/12/2016.
 * <p>
 * I'm still investigating what the best approach to handling the Realm is since we have to close it after every
 * Realm.getDefaultInstance(). In the People screen we get one Realm instance per Fragment and close it
 * in onDestroy (with setRetainInstance(true) onDestroy is only called if the Fragment isn't going to return.)
 * However, here we get the Realm each time we wish to use it and close it straight after.
 * Not easy to test and less efficient but omits the need to use setRetainInstance(true) in the MvpFragment.
 */
public class RealmCacheDiskDataSource<DOMAINTYPE, STORAGETYPE extends RealmModel, QUERY>
	implements ListDiskDataSource<DOMAINTYPE, QUERY> {

	private AllRealmFetcher<STORAGETYPE> mAllRealmFetcher;
	private StorageDataConverter<STORAGETYPE, DOMAINTYPE> mStorageDataConverter;

	public RealmCacheDiskDataSource(Class<STORAGETYPE> classType,
	                                StorageDataConverter<STORAGETYPE, DOMAINTYPE> storageDataConverter) {
		mAllRealmFetcher = new AllRealmFetcher<>(classType);
		mStorageDataConverter = storageDataConverter;
	}

	@Override
	public Observable<List<DOMAINTYPE>> getFromStorage(QUERY dataQuery) {
		return Observable
			.create((Observable.OnSubscribe<List<DOMAINTYPE>>) subscriber -> {
				Realm realm = Realm.getDefaultInstance();
				try {
					RealmResults<STORAGETYPE> realmData = mAllRealmFetcher.getData(realm);
					List<DOMAINTYPE> domainData = mStorageDataConverter.getConvertToDomainFunc().call(realmData);
					subscriber.onNext(domainData);
					subscriber.onCompleted();
				} catch (Exception exception) {
					subscriber.onError(exception);
				} finally {
					realm.close();
				}
			}).compose(new BasicScheduleTransformer<>());
	}

	@Override
	public Observable<List<DOMAINTYPE>> saveToStorage(List<DOMAINTYPE> domainList) {
		return Observable
			.create((Observable.OnSubscribe<List<DOMAINTYPE>>) subscriber -> {
				Realm realm = Realm.getDefaultInstance();
				try {
					List<STORAGETYPE> storageList = mStorageDataConverter.getConvertFromDomainFunc().call(domainList);

					RealmUpdateMethod<List<STORAGETYPE>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mAllRealmFetcher);
					RealmUpdater<List<STORAGETYPE>> realmUpdater = new SyncRealmUpdater<>(realm, realmUpdateMethod);

					realmUpdater.update(storageList);
					subscriber.onNext(domainList);
					subscriber.onCompleted();

				} catch (Exception exception) {
					subscriber.onError(exception);
				} finally {
					realm.close();
				}
			}).compose(new BasicScheduleTransformer<>());
	}

}
