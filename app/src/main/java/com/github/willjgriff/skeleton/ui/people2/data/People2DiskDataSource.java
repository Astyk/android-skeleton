package com.github.willjgriff.skeleton.ui.people2.data;

import com.github.willjgriff.skeleton.data.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.storage.BasicDiskDataSource;

import io.realm.Realm;

/**
 * Created by Will on 24/12/2016.
 */

public class People2DiskDataSource extends BasicDiskDataSource<Person, PeopleQuery> {

	public People2DiskDataSource(Realm realm, Class<Person> classType) {
		super(realm, classType);
	}
}
