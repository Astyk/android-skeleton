package com.github.willjgriff.skeleton.data.models.converters;

import rx.functions.Func1;

/**
 * Created by Will on 29/12/2016.
 */

public interface StorageDataConverter<FROM, TO> {

	ConvertListFromTo<FROM, TO> getConvertToDomainFunc();

	ConvertListFromTo<TO, FROM> getConvertFromDomainFunc();
}
