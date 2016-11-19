package com.github.willjgriff.skeleton.ui.people;

import android.content.Context;
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
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.di.app.AppInjector;
import com.github.willjgriff.skeleton.mvp.BaseMvpFragment;
import com.github.willjgriff.skeleton.ui.navigation.DetailFragmentListener;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.skeleton.ui.people.di.DaggerPeopleComponent;
import com.github.willjgriff.skeleton.ui.people.di.PeopleModule;
import com.github.willjgriff.skeleton.ui.people.mvp.PeoplePresenter;
import com.github.willjgriff.skeleton.ui.people.mvp.PeopleView;
import com.github.willjgriff.skeleton.ui.people.viewholders.PeopleItemViewHolder.PeopleListener;
import com.github.willjgriff.skeleton.ui.utils.ErrorDisplayer;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Will on 17/08/2016.
 */
public class PeopleFragment extends BaseMvpFragment<PeopleView, PeoplePresenter>
	implements PeopleView, PeopleListener {

	@Inject
	PeoplePresenter mPresenter;

	private PeopleAdapter mPeopleAdapter;
	private NavigationToolbarListener mToolbarListener;
	private DetailFragmentListener mDetailFragmentListener;
	private ProgressBar mProgressBar;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
		mDetailFragmentListener = (DetailFragmentListener) context;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupView(view);

		RxSwipeRefreshLayout.refreshes(mSwipeRefreshLayout).subscribe(aVoid -> {
			mPresenter.triggerRefreshFetch();
		});
	}

	private void setupView(View view) {
		mToolbarListener.setToolbarTitle(R.string.fragment_people_title);
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_people_progress_bar);

		setupRecyclerView(view);
	}

	private void setupRecyclerView(View view) {
		RecyclerView peopleRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_people_recycler_view);
		mPeopleAdapter = new PeopleAdapter();
		peopleRecyclerView.setAdapter(mPeopleAdapter);
		peopleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_people_swipe_refresh);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DaggerPeopleComponent.builder()
			.appComponent(AppInjector.INSTANCE.getComponent())
			.peopleModule(new PeopleModule())
			.build()
			.inject(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_people, container, false);
	}

	@Override
	public void onDestroyView() {
		mToolbarListener.hideNetworkLoadingView();
		super.onDestroyView();
	}

	@Override
	protected PeopleView getMvpView() {
		return this;
	}

	@Override
	protected PeoplePresenter createPresenter() {
		return mPresenter;
	}

	@Override
	public void setPeople(List<Person> people) {
		mPeopleAdapter.setPeople(people, this);
	}

	@Override
	public void hideDataLoading() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void hideNetworkLoading() {
		mToolbarListener.hideNetworkLoadingView();
		if (mSwipeRefreshLayout.isRefreshing()) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	public void closeDetailFrament() {
		mDetailFragmentListener.closeDetailFragment();
	}

	@Override
	public void showStorageLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void showNetworkLoading() {
		mToolbarListener.showNetworkLoadingView();
	}

	@Override
	public void handleError(Throwable throwable) {
		ErrorDisplayer.displayError(getView(), throwable);
	}

	@Override
	public void openPersonDetails(Person person) {
		// TODO: We could move this logic to the NavigationActivity if we have a secondary activity that accepts a Fragment
		if (mDetailFragmentListener.twoPaneViewEnabled()) {
			mDetailFragmentListener.openDetailFragment(PersonDetailsFragment.createInstance(person));
		} else {
			startActivity(PersonDetailsActivity.getIntent(getContext(), person));
		}
	}

}
