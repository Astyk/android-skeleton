package com.github.willjgriff.skeleton.ui.land;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.BaseView;

import java.util.List;

/**
 * Created by Will on 31/08/2016.
 */
public interface LandView extends BaseView {

	void setPeople(List<Person> people);

	void showInitialLoading();

	void showNetworkLoading();

	void hideLoading();

	void showError();
}
