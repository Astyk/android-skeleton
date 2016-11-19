package com.github.willjgriff.skeleton.ui.people.mvp;

import com.github.willjgriff.skeleton.data.models.Person;

import java.util.List;

/**
 * Created by Will on 12/11/2016.
 */

public interface PeopleView {

	void setPeople(List<Person> persons);

	void hideDataLoading();

	void hideNetworkLoading();

	void closeDetailFrament();

	void showStorageLoading();

	void showNetworkLoading();

	void handleError(Throwable throwable);
}
