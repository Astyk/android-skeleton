package com.github.willjgriff.skeleton.data.models.helpers;

/**
 * Created by Will on 11/09/2016.
 */
// TODO: This process isn't great, the implementing object must use "timestamp" field
public interface Timestamp {

	// Implementing class MUST have a "timestamp" field. I'm aware how poor this is. Unfortunately
	// Realm objects cannot use polymorphism yet. I should find an alternative to this approach.
	String TIMESTAMP_FIELD = "timestamp";

	void setTimestamp(long timestamp);

	long getTimestamp();
}
