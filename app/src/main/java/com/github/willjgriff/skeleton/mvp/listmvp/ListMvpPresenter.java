package com.github.willjgriff.skeleton.mvp.listmvp;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.mvp.BaseMvpPresenter;

import io.realm.RealmModel;
import timber.log.Timber;

/**
 * Created by Will on 12/11/2016.
 */
public abstract class ListMvpPresenter<TYPE extends RealmModel, VIEW extends ListMvpView<TYPE>>
	extends BaseMvpPresenter<VIEW> {

	public void refreshNews() {
		getRepository().refreshData();
	}

	protected abstract ListCacheRepository<TYPE> getRepository();

	@Override
	public void viewReady() {
		getView().showLoadingView();
		addSubscription(getRepository().getData().subscribe(newsList -> {
			getView().satDataList(newsList);
			getView().hideLoadingView();
		}, throwable -> {
			Timber.e(throwable, "Error fetching data");
			getView().showError(throwable);
		}));
	}

	@Override
	public void close() {
		super.close();
		getRepository().close();
	}
}
