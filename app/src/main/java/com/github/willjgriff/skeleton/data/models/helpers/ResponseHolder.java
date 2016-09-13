package com.github.willjgriff.skeleton.data.models.helpers;

/**
 * Created by Will on 10/09/2016.
 */

public class ResponseHolder<RETURNTYPE> {

	private RETURNTYPE mData;
	private Throwable mError;
	private Source mSource;

	public ResponseHolder(Source source) {
		mSource = source;
	}

	public Source getSource() {
		return mSource;
	}

	public void setData(RETURNTYPE data) {
		mData = data;
	}

	public RETURNTYPE getData() {
		return mData;
	}

	public boolean hasData() {
		return mData != null;
	}

	public Throwable getError() {
		return mError;
	}

	public void setError(Throwable error) {
		mError = error;
	}

	public boolean hasError() {
		return mError != null;
	}

	public enum Source {
		STORAGE,
		NETWORK;
	}
}
