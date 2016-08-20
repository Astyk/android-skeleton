package com.github.willjgriff.skeleton.ui.land;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Question;
import com.github.willjgriff.skeleton.di.questions.QuestionsInjector;
import com.github.willjgriff.skeleton.ui.land.di.DaggerLandComponent;
import com.github.willjgriff.skeleton.ui.land.di.LandModule;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Will on 17/08/2016.
 */

public class LandFragment extends Fragment implements LandContract.View {

	@Inject
	LandPresenter mPresenter;

	private RecyclerView mQuestions;
	private QuestionsAdapter mQuestionsAdapter;
	private NavigationToolbarListener mToolbarListener;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DaggerLandComponent.builder()
			.questionsComponent(QuestionsInjector.INSTANCE.getComponent())
			.landModule(new LandModule(this))
			.build()
			.inject(this);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_land, container, false);

		mQuestions = (RecyclerView) view.findViewById(R.id.fragment_land_questions);
		mQuestionsAdapter = new QuestionsAdapter();
		mQuestions.setAdapter(mQuestionsAdapter);
		mQuestions.setLayoutManager(new LinearLayoutManager(getContext()));

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}

	@Override
	public void onPause() {
		mPresenter.stop();
		super.onPause();
	}

	@Override
	public void setQuestions(List<Question> questions) {
		mQuestionsAdapter.setQuestions(questions);
	}

	@Override
	public void showNetworkLoadingView() {
		mToolbarListener.showNetworkLoadingView();
	}

	@Override
	public void hideNetworkLoadingView() {
		mToolbarListener.hideNetworkLoadingView();
	}

}
