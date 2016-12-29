package com.github.willjgriff.skeleton.data.models.person2;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.functions.Func1;

/**
 * Created by Will on 13/11/2016.
 */

public class StoragePerson2 extends RealmObject {

	@PrimaryKey
	private String email;

	public StoragePerson2() {
	}

	public StoragePerson2(Person2 person) {
		email = person.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static class ConvertToDomain implements Func1<StoragePerson2, Person2> {
		@Override
		public Person2 call(StoragePerson2 storagePerson2) {
			return Person2.builder()
				.setEmail(storagePerson2.getEmail())
				.build();
		}
	}
}
