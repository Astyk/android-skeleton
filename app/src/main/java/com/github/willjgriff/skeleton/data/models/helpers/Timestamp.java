package com.github.willjgriff.skeleton.data.models.helpers;

/**
 * Created by Will on 11/09/2016.
 */

public interface Timestamp {

	public static final String TIMESTAMP_FIELD = "timestamp";

	void setTimestamp(long timestamp);

	long getTimestamp();
}
