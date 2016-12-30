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

public class ListCacheRepository<TYPE, QUERY> {

	private ListNetworkDataSource<TYPE, QUERY> mListNetworkDataSource;
	private ListDiskDataSource<TYPE, QUERY> mListDiskDataSource;
	private PublishSubject<Void> mRefreshTrigger;
	private Observable<List<TYPE>> mReplayObservable;
	private ListCacheListener<TYPE> mListCacheListener;

	public ListCacheRepository(ListNetworkDataSource<TYPE, QUERY> listNetworkDataSource, ListDiskDataSource<TYPE, QUERY> listDiskDataSource) {
		mListNetworkDataSource = listNetworkDataSource;
		mListDiskDataSource = listDiskDataSource;
		mRefreshTrigger = PublishSubject.create();
	}

	public void setListCacheListener(ListCacheListener<TYPE> listCacheListener) {
		mListCacheListener = listCacheListener;
	}

	public Observable<List<TYPE>> getData(QUERY query) {
		if (mReplayObservable == null) {
			mReplayObservable = Observable
				.concat(mListDiskDataSource.getFromStorage(query), getDataFromNetworkTrigger(query))
				.replay(1)
				.autoConnect()
				.filter(dataList -> dataList != null)
				.filter(dataList -> dataList.size() > 0)
				.doOnNext(data -> {
					if (mListCacheListener != null) {
						mListCacheListener.dataLoaded(data);
					}
				});
		}
		return mReplayObservable;
	}

	private Observable<List<TYPE>> getDataFromNetworkTrigger(QUERY query) {
		return mRefreshTrigger
			.startWith((Void) null)
			.flatMap(aVoid -> getDataFromNetwork(query));
	}

	private Observable<List<TYPE>> getDataFromNetwork(QUERY query) {
		return mListNetworkDataSource.getDataFromNetwork(query).doOnNext(dataList -> {
			if (dataList != null) {
				mListDiskDataSource.saveToStorage(dataList);
			}
		});
	}

	public void refreshData() {
		mRefreshTrigger.onNext(null);
	}

	public void close() {
		mListDiskDataSource.close();
	}

	public interface ListCacheListener<TYPE> {

		void dataLoaded(List<TYPE> types);
	}
}
