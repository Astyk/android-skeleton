package com.github.willjgriff.skeleton.data.models.helpers;

/**
 * Created by Will on 10/09/2016.
 */

public class ErrorHolder<RETURNTYPE> {

	private RETURNTYPE mData;
	private Throwable mError;

	public void setData(RETURNTYPE data) {
		mData = data;
	}

	public RETURNTYPE getData() {
		return mData;
	}

	public void setError(Throwable error) {
		mError = error;
	}

	public Throwable getError() {
		return mError;
	}

	public boolean hasError() {
		return mError != null;
	}
}
