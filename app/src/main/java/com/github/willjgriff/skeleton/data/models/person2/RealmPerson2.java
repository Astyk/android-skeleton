package com.github.willjgriff.skeleton.data.models.person2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.functions.Func1;

/**
 * Created by Will on 13/11/2016.
 */

public class RealmPerson2 extends RealmObject {

	@PrimaryKey
	private String email;

	public RealmPerson2() {
	}

	public RealmPerson2(Person2 person) {
		email = person.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
