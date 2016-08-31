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
import com.github.willjgriff.skeleton.data.models.Question;
import com.github.willjgriff.skeleton.ui.land.di.LandInjector;
import com.github.willjgriff.skeleton.ui.navigation.NavigationFragment;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Will on 17/08/2016.
 */

public class LandFragment extends Fragment implements LandView {

	@Inject
	LandPresenter mPresenter;

	private RecyclerView mQuestions;
	private QuestionsAdapter mQuestionsAdapter;
	private NavigationToolbarListener mToolbarListener;
	private ProgressBar mProgressBar;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LandInjector.INSTANCE.getComponent(this).inject(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_land, container, false);

		mQuestions = (RecyclerView) view.findViewById(R.id.fragment_land_questions);
		mQuestionsAdapter = new QuestionsAdapter();
		mQuestions.setAdapter(mQuestionsAdapter);
		mQuestions.setLayoutManager(new LinearLayoutManager(getContext()));
		mToolbarListener.setToolbarTitle(NavigationFragment.LAND.getNavigationTitle());
		mProgressBar = (ProgressBar) view.findViewById(R.id.fragment_land_progress_bar);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.loadData();
	}

	@Override
	public void onPause() {
		mPresenter.cancelLoad();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		mPresenter.closeDatabase();
		super.onDestroy();
	}

	@Override
	public void setQuestions(List<Question> questions) {
		mQuestionsAdapter.setQuestions(questions);
	}

	@Override
	public void showNetworkLoading() {
		mToolbarListener.showNetworkLoadingView();
	}

	@Override
	public void hideLoading() {
		mToolbarListener.hideNetworkLoadingView();
		mProgressBar.setVisibility(View.INVISIBLE);
	}

	@Override
	public void showInitialLoading() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void showError() {
		Snackbar.make(getView(), R.string.fragment_land_error_string, Snackbar.LENGTH_LONG).show();
	}

}
