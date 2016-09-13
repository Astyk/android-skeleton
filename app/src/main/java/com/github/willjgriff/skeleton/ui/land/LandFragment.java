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

import rx.functions.Action1;

/**
 * Created by Will on 17/08/2016.
 */
// TODO: Abstract the View - Presenter binding behaviour into a base class
public class LandFragment extends Fragment implements LandView {

	@Inject
	LandPresenter mPresenter;

	private RecyclerView mPeople;
	private PeopleAdapter mPeopleAdapter;
	private NavigationToolbarListener mToolbarListener;
	private ProgressBar mProgressBar;
	private boolean mOrientationChange;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LandInjector.INSTANCE.getComponent().inject(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_land, container, false);

		mPeople = (RecyclerView) view.findViewById(R.id.fragment_land_people);
		mPeopleAdapter = new PeopleAdapter();
		mPeople.setAdapter(mPeopleAdapter);
		mPeople.setLayoutManager(new LinearLayoutManager(getContext()));
		mToolbarListener.setToolbarTitle(NavigationFragment.LAND.getNavigationTitle());
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_land_progress_bar);

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mPresenter.bindView();

		showInitialLoading();
		showNetworkLoading();

		mPresenter.mListPeople.subscribe(new Action1<List<Person>>() {
			@Override
			public void call(List<Person> persons) {
				setPeople(persons);
			}
		});
		mPresenter.mErrorObservable.subscribe(new Action1<Throwable>() {
			@Override
			public void call(Throwable throwable) {
				showError();
			}
		});

		// TODO: Think about these a bit.
		mPresenter.mPeopleLoadedFromCache.subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean aBoolean) {
				hideInitialLoading();
			}
		});

		mPresenter.mPeopleLoadedFromNetwork.subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean aBoolean) {
				hideInitialLoading();
				hideNetworkLoading();
			}
		});

	}

	// Find a better way to do this.
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mOrientationChange = true;
	}

	@Override
	public void onDestroy() {
		if (!mOrientationChange) {
			mPresenter.cancelLoading();
			LandInjector.INSTANCE.invalidate();
		}
		super.onDestroy();
	}

	@Override
	public void setPeople(List<Person> people) {
		mPeopleAdapter.setPeople(people);
	}

	@Override
	public void showInitialLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideInitialLoading() {
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void showNetworkLoading() {
		mToolbarListener.showNetworkLoadingView();
	}

	@Override
	public void hideNetworkLoading() {
		mToolbarListener.hideNetworkLoadingView();
	}

	@Override
	public void showError() {
		Snackbar.make(getView(), R.string.fragment_land_error_string, Snackbar.LENGTH_LONG).show();
	}

}
