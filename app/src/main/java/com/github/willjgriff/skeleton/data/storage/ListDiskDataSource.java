package com.github.willjgriff.skeleton.data.storage;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Will on 26/11/2016.
 */

public interface ListDiskDataSource<TYPE extends RealmModel, QUERY> {

	Observable<RealmResults<TYPE>> getFromStorage(QUERY query);

	void saveToStorage(List<TYPE> newsList);

	void close();

}
