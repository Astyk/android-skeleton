package com.github.willjgriff.skeleton.data.models.converters;

import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.models.person2.StoragePerson2;

import rx.functions.Func1;

/**
 * Created by Will on 29/12/2016.
 */

public class PersonStorageConverter implements StorageConverter<StoragePerson2, Person2> {

	public ConvertListFromTo<StoragePerson2, Person2> getConvertToDomainFunc() {
		return new ConvertListFromTo<>(new ConvertToDomain());
	}

	public ConvertListFromTo<Person2, StoragePerson2> getConvertFromDomainFunc() {
		return new ConvertListFromTo<>(StoragePerson2::new);
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
