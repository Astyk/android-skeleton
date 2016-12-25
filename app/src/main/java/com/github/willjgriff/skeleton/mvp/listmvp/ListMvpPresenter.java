package com.github.willjgriff.skeleton.mvp.listmvp;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.mvp.BaseMvpPresenter;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by Will on 12/11/2016.
 */
public abstract class ListMvpPresenter<TYPE extends RealmModel, VIEW extends ListMvpView<TYPE>, QUERY>
	extends BaseMvpPresenter<VIEW> {

	public void refreshData() {
		getRepository().refreshData();
	}

	protected abstract ListCacheRepository<TYPE, QUERY> getRepository();

	@Override
	public void viewReady() {
		getView().showLoadingView();
		addSubscription(getDataObservable().subscribe(newsList -> {
			getView().satDataList(newsList);
			getView().hideLoadingView();
		}, throwable -> {
			Timber.e(throwable, "Error fetching data");
			getView().showError(throwable);
		}));
	}

	protected abstract Observable<List<TYPE>> getDataObservable();

	@Override
	public void close() {
		super.close();
		getRepository().close();
	}
}
