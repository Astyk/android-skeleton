package com.github.willjgriff.skeleton.data;

import com.github.willjgriff.skeleton.data.network.ListNetworkDataSource;
import com.github.willjgriff.skeleton.data.storage.ListDiskDataSource;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 21/11/2016.
 */

public class ListCacheRepository<TYPE, QUERY> implements RefreshableRepository {

	private ListNetworkDataSource<TYPE, QUERY> mListNetworkDataSource;
	private ListDiskDataSource<TYPE, QUERY> mListDiskDataSource;
	private Observable<Void> mRefreshTrigger;
	private Observable<List<TYPE>> mReplayObservable;

	public ListCacheRepository(ListNetworkDataSource<TYPE, QUERY> listNetworkDataSource, ListDiskDataSource<TYPE, QUERY> listDiskDataSource) {
		mListNetworkDataSource = listNetworkDataSource;
		mListDiskDataSource = listDiskDataSource;
	}

	@Override
	public void setRefreshTrigger(Observable<Void> refreshTrigger) {
		mRefreshTrigger = refreshTrigger;
	}

	public Observable<List<TYPE>> getData(QUERY query) {
		if (mReplayObservable == null) {
			mReplayObservable = Observable
				.concat(mListDiskDataSource.getFromStorage(query), getDataFromNetworkTrigger(query))
				.replay(1)
				.autoConnect()
				.filter(dataList -> dataList != null && dataList.size() > 0);
		}
		return mReplayObservable;
	}

	private Observable<List<TYPE>> getDataFromNetworkTrigger(QUERY query) {
		return mRefreshTrigger
			.startWith((Void) null)
			.flatMap(aVoid -> getDataFromNetwork(query));
	}

	private Observable<List<TYPE>> getDataFromNetwork(QUERY query) {
		return mListNetworkDataSource.getDataFromNetwork(query)
			.flatMap(dataList -> mListDiskDataSource.saveToStorage(dataList));
	}
}
