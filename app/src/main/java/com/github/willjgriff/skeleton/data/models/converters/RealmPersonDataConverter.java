package com.github.willjgriff.skeleton.data.models.converters;

import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.models.person2.RealmPerson2;

import rx.functions.Func1;

/**
 * Created by Will on 29/12/2016.
 */

public class RealmPersonDataConverter implements StorageDataConverter<RealmPerson2, Person2> {

	public ConvertListFromTo<RealmPerson2, Person2> getConvertToDomainFunc() {
		return new ConvertListFromTo<>(new ConvertToDomain());
	}

	public ConvertListFromTo<Person2, RealmPerson2> getConvertFromDomainFunc() {
		return new ConvertListFromTo<>(RealmPerson2::new);
	}

	public static class ConvertToDomain implements Func1<RealmPerson2, Person2> {
		@Override
		public Person2 call(RealmPerson2 realmPerson2) {
			return Person2.builder()
				.setEmail(realmPerson2.getEmail())
				.build();
		}
	}
}
