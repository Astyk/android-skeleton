package com.github.willjgriff.skeleton.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Will on 06/09/2016.
 */
public class Person extends RealmObject implements Timestamp, Parcelable {

	private long timestamp;

	@PrimaryKey
	@Expose
	@SerializedName("email")
	private String email;

	public Person() {
	}

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

	protected Person(Parcel in) {
		timestamp = in.readLong();
		email = in.readString();
	}

	public static final Creator<Person> CREATOR = new Creator<Person>() {
		@Override
		public Person createFromParcel(Parcel in) {
			return new Person(in);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeLong(timestamp);
		parcel.writeString(email);
	}
}
