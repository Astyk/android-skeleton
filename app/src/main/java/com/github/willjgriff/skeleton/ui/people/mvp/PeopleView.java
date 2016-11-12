package com.github.willjgriff.skeleton.ui.people.mvp;

import com.github.willjgriff.skeleton.data.models.Person;

import java.util.List;

/**
 * Created by Will on 12/11/2016.
 */

public interface PeopleView {

	void setPeople(List<Person> persons);

	void hideStorageLoading();

	void hideNetworkLoading();

	void closeDetailFrament();

	void showStorageError();

	void showNetworkError(Throwable throwable);

	void showStorageLoading();

	void showNetworkLoading();
}
