package com.github.willjgriff.skeleton.data.models.converters;

import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.models.person2.StoragePerson2;

/**
 * Created by Will on 29/12/2016.
 */

public class PersonStorageConverter implements StorageConverter<StoragePerson2, Person2> {

	public ConvertListFromTo<StoragePerson2, Person2> getConvertToDomainFunc() {
		return new ConvertListFromTo<>(new StoragePerson2.ConvertToDomain());
	}

	public ConvertListFromTo<Person2, StoragePerson2> getConvertFromDomainFunc() {
		return new ConvertListFromTo<>(StoragePerson2::new);
	}
}
