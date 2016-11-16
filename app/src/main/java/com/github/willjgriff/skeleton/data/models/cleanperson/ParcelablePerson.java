package com.github.willjgriff.skeleton.data.models.cleanperson;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Will on 15/11/2016.
 */

public class ParcelablePerson implements Parcelable {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ParcelablePerson(DomainPerson person) {
		email = person.getEmail();
	}

	public DomainPerson convertToDomain() {
		DomainPerson domainPerson = new DomainPerson();
		domainPerson.setEmail(email);
		return domainPerson;
	}

	protected ParcelablePerson(Parcel in) {
		email = in.readString();
	}

	public static final Creator<ParcelablePerson> CREATOR = new Creator<ParcelablePerson>() {
		@Override
		public ParcelablePerson createFromParcel(Parcel in) {
			return new ParcelablePerson(in);
		}

		@Override
		public ParcelablePerson[] newArray(int size) {
			return new ParcelablePerson[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(email);
	}
}
