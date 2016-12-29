package com.github.willjgriff.skeleton.data.models.converters;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by Will on 29/12/2016.
 */
public class ConvertListFromTo<FROM, TO> implements Func1<List<FROM>, List<TO>> {

	private Func1<FROM, TO> mConvertToDomainFunc;

	public ConvertListFromTo(Func1<FROM, TO> convertToDomainFunc) {
		mConvertToDomainFunc = convertToDomainFunc;
	}

	@Override
	public List<TO> call(List<FROM> fromList) {
		List<TO> toList = new ArrayList<>();
		for (FROM fromElement : fromList) {
			toList.add(mConvertToDomainFunc.call(fromElement));
		}
		return toList;
	}
}
