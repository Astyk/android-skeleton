package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.Question;
import com.github.willjgriff.skeleton.mvp.BaseView;

import java.util.List;

/**
 * Created by Will on 31/08/2016.
 */
public interface LandView extends BaseView {

	void setQuestions(List<Question> questions);

	void showNetworkLoading();

	void hideLoading();

	void showInitialLoading();

	void showError();
}
