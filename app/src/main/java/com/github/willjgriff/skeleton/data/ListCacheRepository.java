//package com.github.willjgriff.skeleton.data;
//
//import com.github.willjgriff.ewsgroup.data.storage.ListStorageDataSource;
//
//import java.util.List;
//
//import io.realm.RealmModel;
//import rx.Observable;
//import rx.subjects.PublishSubject;
//
///**
// * Created by Will on 21/11/2016.
// */
//
//public class ListCacheRepository<TYPE extends RealmModel> {
//
//	private ListNetworkDataSource<TYPE> mListNetworkDataSource;
//	private ListStorageDataSource<TYPE> mListStorageDataSource;
//	private PublishSubject<Void> mRefreshTrigger;
//	private Observable<List<TYPE>> mReplayObservable;
//	private ListCacheListener<TYPE> mListCacheListener;
//
//	public ListCacheRepository(ListNetworkDataSource<TYPE> listNetworkDataSource, ListStorageDataSource<TYPE> listStorageDataSource) {
//		mListNetworkDataSource = listNetworkDataSource;
//		mListStorageDataSource = listStorageDataSource;
//		mRefreshTrigger = PublishSubject.create();
//	}
//
//	public void setListCacheListener(ListCacheListener<TYPE> listCacheListener) {
//		mListCacheListener = listCacheListener;
//	}
//
//	public Observable<List<TYPE>> getData() {
//		if (mReplayObservable == null) {
//			mReplayObservable = Observable
//				.concat(mListStorageDataSource.getFromStorage(), getDataFromNetworkTrigger())
//				.replay(1)
//				.autoConnect()
//				.filter(dataList -> dataList.size() > 0)
//				.doOnNext(data -> {
//					if (mListCacheListener != null) {
//						mListCacheListener.dataLoaded(data);
//					}
//				});
//		}
//		return mReplayObservable;
//	}
//
//	private Observable<List<TYPE>> getDataFromNetworkTrigger() {
//		return mRefreshTrigger
//			.startWith((Void) null)
//			.flatMap(aVoid -> getDataFromNetwork());
//	}
//
//	private Observable<List<TYPE>> getDataFromNetwork() {
//		return mListNetworkDataSource.getDataFromNetwork().doOnNext(dataList -> {
//			if (dataList != null) {
//				mListStorageDataSource.saveToStorage(dataList);
//			}
//		});
//	}
//
//	public void refreshData() {
//		mRefreshTrigger.onNext(null);
//	}
//
//	public void close() {
//		mListStorageDataSource.close();
//	}
//
//	public interface ListCacheListener<TYPE> {
//
//		void dataLoaded(List<TYPE> types);
//	}
//}
