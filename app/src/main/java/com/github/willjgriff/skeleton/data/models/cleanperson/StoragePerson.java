package com.github.willjgriff.skeleton.data.models.cleanperson;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.functions.Func1;

/**
 * Created by Will on 13/11/2016.
 */

public class StoragePerson extends RealmObject {

	@PrimaryKey
	private String email;

	public StoragePerson() {
	}

	public StoragePerson(DomainPerson person) {
		email = person.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public class ConvertToDomain implements Func1<StoragePerson, DomainPerson> {
		@Override
		public DomainPerson call(StoragePerson storagePerson) {
			DomainPerson domainPerson = new DomainPerson();
			domainPerson.setEmail(storagePerson.getEmail());
			return domainPerson;
		}
	}
}
