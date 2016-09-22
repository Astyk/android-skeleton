package com.github.willjgriff.skeleton.ui.land;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.RxFragment;
import com.github.willjgriff.skeleton.ui.ErrorDisplayer;
import com.github.willjgriff.skeleton.ui.land.di.LandInjector;
import com.github.willjgriff.skeleton.ui.navigation.NavigationFragment;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Will on 17/08/2016.
 */
public class LandFragment extends RxFragment {

	@Inject
	LandPresenter mPresenter;

	private PeopleAdapter mPeopleAdapter;
	private NavigationToolbarListener mToolbarListener;
	private ProgressBar mProgressBar;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_land, container, false);
	}

	@Override
	public void onDestroy() {
		mPresenter.cancelUpdate();
		mToolbarListener.hideNetworkLoadingView();
		super.onDestroy();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LandInjector.INSTANCE.getComponent().inject(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupView(view);
		setupSubscriptions();
	}

	private void setupView(View view) {
		RecyclerView people = (RecyclerView) view.findViewById(R.id.fragment_land_people);
		mPeopleAdapter = new PeopleAdapter();
		people.setAdapter(mPeopleAdapter);
		people.setLayoutManager(new LinearLayoutManager(getContext()));
		mToolbarListener.setToolbarTitle(NavigationFragment.LAND.getNavigationTitle());
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_land_progress_bar);
		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_land_swipe_refresh);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);

		showCacheLoading();
		showNetworkLoading();
	}

	private void setupSubscriptions() {
		RxSwipeRefreshLayout.refreshes(mSwipeRefreshLayout).subscribe(aVoid -> {
			mPresenter.triggerRefreshFetch();
		});

		addSubscription(mPresenter.getPeopleList().subscribe(this::setPeople));

		addSubscription(mPresenter.getCacheLoaded().subscribe(aBoolean -> {
			hideCacheLoading();
		}));

		addSubscription(mPresenter.getNetworkLoaded().subscribe(aBoolean -> {
			hideNetworkLoading();
			hideCacheLoading();
		}));

		addSubscription(mPresenter.getCacheErrors().subscribe(throwable -> {
			showCacheError();
			hideCacheLoading();
		}));

		addSubscription(mPresenter.getNetworkErrors().subscribe(throwable -> {
			showNetworkError(throwable);
			hideNetworkLoading();
			hideCacheLoading();
		}));

		mPresenter.triggerInitialFetch();
	}

	public void showCacheLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	public void showNetworkLoading() {
		mToolbarListener.showNetworkLoadingView();
	}

	private void showCacheError() {
		Snackbar.make(getView(), R.string.fragment_land_cache_error_string, Snackbar.LENGTH_LONG).show();
	}

	private void hideCacheLoading() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	private void showNetworkError(Throwable throwable) {
		ErrorDisplayer.displayNetworkError(getView(), throwable);
	}

	private void hideNetworkLoading() {
		mToolbarListener.hideNetworkLoadingView();
		if (mSwipeRefreshLayout.isRefreshing()) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	private void setPeople(List<Person> people) {
		mPeopleAdapter.setPeople(people);
	}
}
