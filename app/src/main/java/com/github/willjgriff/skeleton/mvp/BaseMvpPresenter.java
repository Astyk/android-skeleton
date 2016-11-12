package com.github.willjgriff.skeleton.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Will on 09/11/2016.
 */

public abstract class BaseMvpPresenter<VIEW> implements MvpPresenter<VIEW> {

	private VIEW mMvpView;
	private CompositeSubscription mCompositeSubscription;

	@Override
	public final void bindView(VIEW mvpView) {
		mMvpView = mvpView;
		mCompositeSubscription = new CompositeSubscription();
		viewReady();
	}

	/**
	 * Must be used with {@link BaseMvpPresenter#addSubscription} to ensure view updates
	 * are only attempted between {@link BaseMvpPresenter#bindView(Object)} and
	 * {@link BaseMvpPresenter#unbindView()} alternatively check {@link BaseMvpPresenter#getView()}
	 * != null before using, although question the setup if this needs to be done.
	 */
	protected abstract void viewReady();

	@Override
	public final void unbindView() {
		mCompositeSubscription.unsubscribe();
		mMvpView = null;
	}

	@Override
	public void close() {
	}

	public void addSubscription(Subscription subscription) {
		mCompositeSubscription.add(subscription);
	}

	public VIEW getView() {
		return mMvpView;
	}
}
