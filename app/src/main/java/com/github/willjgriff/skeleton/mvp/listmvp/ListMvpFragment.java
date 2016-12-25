package com.github.willjgriff.skeleton.mvp.listmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.network.utils.ConnectivityUtils;
import com.github.willjgriff.skeleton.mvp.BaseMvpFragment;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder.ListItemListener;
import com.github.willjgriff.skeleton.ui.utils.ErrorDisplayer;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;

import java.util.List;

import io.realm.RealmModel;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Will on 28/09/2016.
 */

public abstract class ListMvpFragment<TYPE extends RealmModel, VIEW extends ListMvpView<TYPE>, PRESENTER extends ListMvpPresenter<TYPE, VIEW, QUERY>, VIEWHOLDER extends ListMvpViewHolder<TYPE>, QUERY>
	extends BaseMvpFragment<VIEW, PRESENTER>
	implements ListMvpView<TYPE>, ListItemListener<TYPE> {

	private RecyclerView mRecyclerView;
	private ListMvpAdapter<TYPE, VIEWHOLDER> mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ProgressBar mProgressBar;
	private CompositeSubscription mViewComposite;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mViewComposite = new CompositeSubscription();
		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupAdapter();
		setupRecyclerView(view);
		setupSwipeRefreshLayout(view);
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_list_progress_bar);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mViewComposite.clear();
	}

	private void setupAdapter() {
		mAdapter = createAdapter();
		mAdapter.setListItemListener(this);
	}

	private void setupRecyclerView(View view) {
		mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setAdapter(mAdapter);
	}

	private void setupSwipeRefreshLayout(View view) {
		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_list_swipe_refresh);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);
		Observable<Void> swipeRefreshObservable = RxSwipeRefreshLayout.refreshes(mSwipeRefreshLayout).share();

		swipeRefreshObservable
			.filter(aVoid -> ConnectivityUtils.isConnected(getContext()))
			.subscribe(aVoid -> {
				getPresenter().refreshData();
			});

		swipeRefreshObservable
			.filter(aVoid -> !ConnectivityUtils.isConnected(getContext()))
			.subscribe(aVoid -> {
				mSwipeRefreshLayout.setRefreshing(false);
				ErrorDisplayer.displayError(getView(), new Throwable());
			});
	}

	protected abstract ListMvpAdapter<TYPE, VIEWHOLDER> createAdapter();

	@Override
	public void satDataList(List<TYPE> dataList) {
		mAdapter.setDataList(dataList);
	}

	@Override
	public void showLoadingView() {
		mProgressBar.setVisibility(View.VISIBLE);
		mRecyclerView.setVisibility(View.GONE);
	}

	@Override
	public void hideLoadingView() {
		mProgressBar.setVisibility(View.GONE);
		mRecyclerView.setVisibility(View.VISIBLE);
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showError(Throwable throwable) {
		ErrorDisplayer.displayError(getView(), throwable);
	}
}
