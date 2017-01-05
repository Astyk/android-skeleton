package com.github.willjgriff.skeleton.mvp.listmvp;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.data.RefreshableRepository;
import com.github.willjgriff.skeleton.mvp.BaseMvpPresenter;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by Will on 12/11/2016.
 */
public abstract class ListMvpPresenter<TYPE, VIEW extends ListMvpView<TYPE>, QUERY>
	extends BaseMvpPresenter<VIEW> {

	public void setRefreshTrigger(Observable<Void> swipeRefreshObservable) {
		getRepository().setRefreshTrigger(swipeRefreshObservable);
	}

	protected abstract RefreshableRepository getRepository();

	@Override
	public void viewReady() {
		getView().showLoadingView();
		addSubscription(getDataObservable().subscribe(dataList -> {
			dataLoaded(dataList);
			getView().satDataList(dataList);
			getView().hideLoadingView();
		}, throwable -> {
			Timber.e(throwable, "Error fetching data");
			getView().showError(throwable);
		}));
	}

	/**
	 * Override if we want to do more than just set the data on the list once it's loaded.
	 */
	protected void dataLoaded(List<TYPE> dataList) {
	}

	protected abstract Observable<List<TYPE>> getDataObservable();

}
