package com.github.willjgriff.skeleton.data.utils.response;

/**
 * Created by Will on 10/09/2016.
 */

public class ResponseHolder<RETURNTYPE> {

	private RETURNTYPE mData;
	private Source mSource;

	public ResponseHolder(Source source) {
		mSource = source;
	}

	public Source getSource() {
		return mSource;
	}

	public RETURNTYPE getData() {
		return mData;
	}

	public ResponseHolder<RETURNTYPE> setData(RETURNTYPE data) {
		mData = data;
		return this;
	}

	public boolean hasData() {
		return mData != null;
	}

	public enum Source {
		STORAGE,
		NETWORK;
	}
}
