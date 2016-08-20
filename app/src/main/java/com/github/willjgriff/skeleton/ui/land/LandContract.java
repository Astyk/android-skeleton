package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.Question;
import com.github.willjgriff.skeleton.mvp.BasePresenter;
import com.github.willjgriff.skeleton.mvp.BaseView;

import java.util.List;

/**
 * Created by Will on 19/08/2016.
 */

public interface LandContract {

	interface View extends BaseView {

		void setQuestions(List<Question> questions);

		void showNetworkLoadingView();

		void hideNetworkLoadingView();
	}

	// Maybe we don't need this Interface. Especially if we inject the Presenter
	// using Constructor injection. In which case we won't use it.
	interface Presenter extends BasePresenter {

	}
}
