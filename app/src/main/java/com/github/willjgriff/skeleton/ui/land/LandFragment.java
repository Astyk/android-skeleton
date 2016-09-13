package com.github.willjgriff.skeleton.ui.land;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.land.di.LandInjector;
import com.github.willjgriff.skeleton.ui.navigation.NavigationFragment;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Will on 17/08/2016.
 */
// TODO: Abstract the View - Presenter binding behaviour into a base class
public class LandFragment extends Fragment {

	@Inject
	LandPresenter mPresenter;

	private PeopleAdapter mPeopleAdapter;
	private NavigationToolbarListener mToolbarListener;
	private ProgressBar mProgressBar;
	private CompositeSubscription mCompositeSubscription;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LandInjector.INSTANCE.getComponent().inject(this);
		// Is this safer than the onSaveInstanceState method?
		setRetainInstance(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mCompositeSubscription = new CompositeSubscription();
		return inflater.inflate(R.layout.fragment_land, container, false);
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

		showInitialLoading();
		showNetworkLoading();
	}

	private void setupSubscriptions() {
		addSubscription(mPresenter.getPeopleList().subscribe(this::setPeople));
		addSubscription(mPresenter.getCacheErrors().subscribe(throwable -> {
			showCacheError();
			hideCacheLoading();
		}));
		addSubscription(mPresenter.getNetworkErrors().subscribe(throwable -> {
			showNetworkError();
			hideNetworkLoading();
			hideCacheLoading();
		}));

		// TODO: Think about the ordering of these loading states when we use a merge.
		addSubscription(mPresenter.getCacheLoaded()
			.subscribe(aBoolean -> {
				hideCacheLoading();
			}));
		addSubscription(mPresenter.getNetworkLoaded()
			.subscribe(aBoolean -> {
				hideNetworkLoading();
				hideCacheLoading();
			}));
	}

	public void showInitialLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	public void showNetworkLoading() {
		mToolbarListener.showNetworkLoadingView();
	}

	private void addSubscription(Subscription subscription) {
		mCompositeSubscription.add(subscription);
	}

	private void showCacheError() {
		Snackbar.make(getView(), R.string.fragment_land_cache_error_string, Snackbar.LENGTH_LONG).show();
	}

	private void hideCacheLoading() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	private void showNetworkError() {
		Snackbar.make(getView(), R.string.fragment_land_network_error_string, Snackbar.LENGTH_LONG).show();
	}

	private void hideNetworkLoading() {
		mToolbarListener.hideNetworkLoadingView();
	}

	private void setPeople(List<Person> people) {
		mPeopleAdapter.setPeople(people);
	}

	@Override
	public void onDestroyView() {
		mCompositeSubscription.unsubscribe();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		mPresenter.cancelUpdate();
		super.onDestroy();
	}
}
