package com.github.willjgriff.skeleton.ui.people2.data;

import com.github.willjgriff.skeleton.data.query.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.storage.SharedPrefsListDataSource;
import com.github.willjgriff.skeleton.data.storage.sharedprefs.SharedPreferencesManager;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Will on 30/12/2016.
 */

public class People2SharedPrefsDataSource extends SharedPrefsListDataSource<Person2, PeopleQuery> {

	private static final String PREFS_KEY_LIST_PEOPLE = "com.github.willjgriff.skeletion.ui.people2.data.People2SharedPrefsDataSource;PREFS_KEY_LIST_PEOPLE";

	public People2SharedPrefsDataSource(SharedPreferencesManager sharedPreferencesManager) {
		super(sharedPreferencesManager);
	}

	@Override
	protected String getKey() {
		return PREFS_KEY_LIST_PEOPLE;
	}

	@Override
	protected TypeToken<List<Person2>> getTypeToken() {
		return new TypeToken<List<Person2>>() {};
	}

}
