package com.github.willjgriff.skeleton.data.models;

import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Will on 06/09/2016.
 */
public class Person extends RealmObject implements Timestamp {

	private long timestamp;

	@PrimaryKey
	@Expose
	@SerializedName("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}
}
