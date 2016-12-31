package com.github.willjgriff.skeleton.ui.people.data.datasources;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.storage.realmfetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.realmfetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.AsyncRealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.RealmUpdateMethod;
import com.github.willjgriff.skeleton.data.storage.realmupdaters.methods.ReplaceListRealmUpdateMethod;
import com.github.willjgriff.skeleton.data.utils.response.RealmResponseTransformer;
import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by Will on 08/11/2016.
 */

public class PeopleStorageDataSource {

	private Realm mRealm;
	private RealmFetcher<Person> mPeopleRealmFetcher;

	public PeopleStorageDataSource(@NonNull Realm realm) {
		mRealm = realm;
		mPeopleRealmFetcher = new AllRealmFetcher<>(Person.class);
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleFromStorage() {
		return mPeopleRealmFetcher.getDataAsyncObservable(mRealm).compose(new RealmResponseTransformer<>());
	}

	public void savePeopleToStorage(List<Person> people) {
		RealmUpdateMethod<List<Person>> realmUpdateMethod = new ReplaceListRealmUpdateMethod<>(mPeopleRealmFetcher);
		RealmUpdater<List<Person>> realmAsyncUpdater = new AsyncRealmUpdater<>(mRealm, realmUpdateMethod);
		realmAsyncUpdater.update(people);
	}

	public void close() {
		mRealm.close();
	}
}
